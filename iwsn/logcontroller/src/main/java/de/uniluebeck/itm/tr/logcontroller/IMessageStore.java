/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or        *
 *   promote products derived from this software without specific prior written permission.                           *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/
package de.uniluebeck.itm.tr.logcontroller;

import eu.wisebed.testbed.api.wsn.v211.Message;
import eu.wisebed.testbed.api.wsn.v211.MessageType;
import eu.wisebed.testbed.api.wsn.v211.SecretReservationKey;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * Interface for fetching of logged Messages
 */
@WebService(name = "MessageStore", targetNamespace = "urn:MessageStore")
public interface IMessageStore {

    /**
     * tests if messages for an reservationkey are stored
     * @param secretReservationKey
     * @return true or false
     */
    @WebMethod(operationName = "hasMessages")
    @WebResult(name = "messages-found")
    boolean hasMessages(@WebParam(name = "secretReservationKey") SecretReservationKey secretReservationKey);

    /**
     * fetches Messages for multiple reservationkeys
     * @param secretReservationKey
     * @param messageType TEXT for Textmessages, BINARY for Binarymessages,
     *        NULL for all Messages
     * @param limit maximum number of messages, 0 for unlimited
     * @return Array of Messages
     */
    @WebMethod(operationName = "fetchMessages")
    @WebResult(name = "messages")
    Message[] fetchMessages(@WebParam(name = "secretReservationKey") List<SecretReservationKey> secretReservationKey,
                            @WebParam(name = "messageType") MessageType messageType,
                            @WebParam(name = "messageLimit") int limit);
}
