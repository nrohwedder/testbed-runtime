package de.uniluebeck.itm.tr.runtime.portalapp.protobuf;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.uniluebeck.itm.tr.runtime.portalapp.SessionManagementServiceImpl;
import de.uniluebeck.itm.tr.runtime.portalapp.WSNServiceHandle;
import de.uniluebeck.itm.tr.runtime.wsnapp.WSNApp;
import de.uniluebeck.itm.tr.runtime.wsnapp.WSNAppMessages;
import eu.wisebed.testbed.api.wsn.v211.SecretReservationKey;
import eu.wisebed.testbed.api.wsn.v211.UnknownNodeUrnException_Exception;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ProtobufControllerServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger log = LoggerFactory.getLogger(ProtobufControllerServerHandler.class.getName());

	private boolean firstMessage = true;

	private ProtobufControllerServer protobufControllerServer;

	private final SessionManagementServiceImpl sessionManagement;

	private Channel channel;

	private WSNServiceHandle wsnServiceHandle;

	private String secretReservationKey;

	public ProtobufControllerServerHandler(final ProtobufControllerServer protobufControllerServer,
										   final SessionManagementServiceImpl sessionManagement) {
		this.protobufControllerServer = protobufControllerServer;
		this.sessionManagement = sessionManagement;
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		log.info("Client connected: {}", e);
		channel = e.getChannel();
		super.channelConnected(ctx, e);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		log.info("Client disconnected: {}", e);
		wsnServiceHandle.getProtobufControllerHelper().removeChannel(e.getChannel());
		protobufControllerServer.removeHandler(this);
		channel = null;
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		WisebedProtocol.Envelope envelope = (WisebedProtocol.Envelope) e.getMessage();
		switch (envelope.getBodyType()) {
			case SECRET_RESERVATION_KEYS:
				receivedSecretReservationKeys(ctx, e, envelope);
				break;
			case MESSAGE:
				receivedMessage(ctx, e, envelope);
				break;
			default:
				log.warn("Received message other than secret reservation keys which is not allowed.");
				e.getChannel().close();
				break;
		}

	}

	private void receivedMessage(final ChannelHandlerContext ctx, final MessageEvent e,
								 final WisebedProtocol.Envelope envelope) {

		log.debug("ProtobufControllerServerHandler.receivedMessage({}, {}, {})", new Object[]{ctx, e, envelope});

		if (firstMessage) {
			log.warn("Received message before receiving secret reservation keys. Closing channel: {}", channel);
			ctx.getChannel().close();
			return;
		}

		final WisebedProtocol.Message message = envelope.getMessage();


		final HashSet<String> nodeUrns;
		final WSNAppMessages.Message wsnAppMessage;

		if (message.hasNodeBinary()) {

			WSNAppMessages.Message.BinaryMessage.Builder wsnAppBinaryMessageBuilder =
					WSNAppMessages.Message.BinaryMessage.newBuilder()
							.setBinaryType(message.getNodeBinary().getType())
							.setBinaryData(message.getNodeBinary().getData());

			wsnAppMessage = WSNAppMessages.Message.newBuilder()
					.setTimestamp(message.getTimestamp())
					.setSourceNodeId(message.getNodeBinary().getSourceNodeUrn())
					.setBinaryMessage(wsnAppBinaryMessageBuilder)
					.build();

			nodeUrns = Sets.newHashSet(message.getNodeBinary().getDestinationNodeUrnsList());


		} else if (message.hasNodeText()) {

			WSNAppMessages.Message.TextMessage.Builder textMessageBuilder =
					WSNAppMessages.Message.TextMessage.newBuilder()
							.setMessageLevel(
									WSNAppMessages.Message.MessageLevel.valueOf(
											message.getNodeText().getLevel().getNumber()
									)
							)
							.setMsg(message.getNodeText().getText());

			wsnAppMessage = WSNAppMessages.Message.newBuilder()
					.setTimestamp(message.getTimestamp())
					.setSourceNodeId(message.getNodeText().getSourceNodeUrn())
					.setTextMessage(textMessageBuilder)
					.build();

			nodeUrns = Sets.newHashSet(message.getNodeText().getDestinationNodeUrnsList());

		} else {
			log.warn("Received invalid message. Ignoring: {}", message);
			return;
		}

		try {
			log.debug("Sending message {} to nodeUrns {}", wsnAppMessage, nodeUrns);
			wsnServiceHandle.getWsnApp().send(
					nodeUrns,
					wsnAppMessage,
					new WSNApp.Callback() {
						@Override
						public void receivedRequestStatus(final WSNAppMessages.RequestStatus requestStatus) {
							if (requestStatus.getStatus().getValue() != 1) {
								String text = requestStatus.getStatus().getNodeId() + ": " +
										"\"" + requestStatus.getStatus().getMsg() + "\"" +
										" (value=" + requestStatus.getStatus().getValue() + ")";
								log.warn(text);
								sendBackendMessage(ctx, e.getRemoteAddress(), text);
							}
						}

						@Override
						public void failure(final Exception exception) {
							String text = "Message delivery to " + nodeUrns + " failed. Reason: " + exception
									.getMessage();
							log.error(text);
							sendBackendMessage(ctx, e.getRemoteAddress(), text);
						}
					}
			);
		} catch (UnknownNodeUrnException_Exception exception) {
			String text = "Message delivery to " + nodeUrns + " failed. Reason: " + exception.getMessage();
			log.error(text);
			sendBackendMessage(ctx, e.getRemoteAddress(), text);
		}


	}

	private void sendBackendMessage(final ChannelHandlerContext ctx, final SocketAddress remoteAddress,
									final String text) {

		WisebedProtocol.Message.Backend.Builder backendBuilder = WisebedProtocol.Message.Backend.newBuilder()
				.setLevel(WisebedProtocol.Message.Level.WARN)
				.setText(text);
		WisebedProtocol.Message.Builder messageBuilder = WisebedProtocol.Message.newBuilder()
				.setType(WisebedProtocol.Message.Type.BACKEND)
				.setBackend(backendBuilder);
		WisebedProtocol.Envelope envelope = WisebedProtocol.Envelope.newBuilder()
				.setMessage(messageBuilder)
				.setBodyType(WisebedProtocol.Envelope.BodyType.MESSAGE)
				.build();
		DefaultChannelFuture future = new DefaultChannelFuture(ctx.getChannel(), true);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(final ChannelFuture future) throws Exception {
				if (future.isCancelled() && log.isWarnEnabled()) {
					log.warn("Delivery of backend message ({}) to {} was cancelled.", text, remoteAddress);
				} else if (future.isSuccess() && log.isTraceEnabled()) {
					log.trace("Delivery of backend message ({}) to {} successful.", text, remoteAddress);
				}
			}
		}
		);
		ctx.sendDownstream(new DownstreamMessageEvent(ctx.getChannel(), future, envelope, remoteAddress));

	}

	private void receivedSecretReservationKeys(final ChannelHandlerContext ctx, final MessageEvent e,
											   final WisebedProtocol.Envelope envelope) {

		if (firstMessage) {
			firstMessage = false;
		}

		checkArgument(!firstMessage, "Secret reservation keys are only allowed to be sent as the very first message.");
		checkArgument(envelope.hasSecretReservationKeys(), "Envelope is missing secret reservation keys");

		log.debug("ProtobufControllerServerHandler.receivedSecretReservationKeys()");

		secretReservationKey = envelope.getSecretReservationKeys().getKeys(0).getKey();
		wsnServiceHandle = sessionManagement.getWsnServiceHandle(secretReservationKey);

		if (wsnServiceHandle == null) {

			log.debug(
					"Received reservation key for either unknown or not yet started WSN instance. Trying to start instance..."
			);
			List<SecretReservationKey> secretReservationKeys = convert(envelope.getSecretReservationKeys());

			try {
				sessionManagement.getInstance(secretReservationKeys, "NONE");
				wsnServiceHandle = sessionManagement.getWsnServiceHandle(secretReservationKey);
			} catch (Exception e1) {
				log.warn("" + e1, e1);
				channel.close();
				return;
			}

			if (wsnServiceHandle == null) {
				log.debug("Invalid secret reservation key. Closing channel.");
				channel.close();
				return;
			}

		}

		log.debug("Valid secret reservation key. Adding listener to WSN App instance.");
		wsnServiceHandle.getProtobufControllerHelper().addChannel(e.getChannel());
		protobufControllerServer.addHandler(this);

	}

	private List<SecretReservationKey> convert(WisebedProtocol.SecretReservationKeys secretReservationKeys) {
		List<SecretReservationKey> retKeys = Lists.newArrayList();
		for (WisebedProtocol.SecretReservationKeys.SecretReservationKey key : secretReservationKeys.getKeysList()) {
			retKeys.add(convert(key));
		}
		return retKeys;
	}

	private SecretReservationKey convert(WisebedProtocol.SecretReservationKeys.SecretReservationKey key) {
		SecretReservationKey retKey = new SecretReservationKey();
		retKey.setUrnPrefix(key.getUrnPrefix());
		retKey.setSecretReservationKey(key.getKey());
		return retKey;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log.warn("Unexpected exception from downstream: {}", e);
		e.getChannel().close();
	}

	public void stop() {
		log.debug("Stopping ProtobufControllerHandler for channel {}...", channel);
		// if the channel object is null it has already been closed
		if (channel != null) {
			channel.close();
		}
	}

	public String getSecretReservationKey() {
		return secretReservationKey;
	}
}
