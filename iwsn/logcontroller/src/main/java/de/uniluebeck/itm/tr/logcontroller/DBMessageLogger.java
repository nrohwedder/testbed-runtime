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

import com.google.common.base.Preconditions;
import eu.wisebed.testbed.api.wsn.v211.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * saves all messages to the database
 */
public class DBMessageLogger implements IMessageListener {

    private static final Logger _log = LoggerFactory.getLogger(DBMessageLogger.class);

    private EntityManager _manager;

    @Override
    public void init(Map properties) {
        Preconditions.checkNotNull(properties, "Properties are null!");
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory(Server.PERSISTENCE_CONTEXT, properties);
        _manager = factory.createEntityManager();
        _log.debug("JPA-Entitymanager created");
    }

    @Override
    public void newMessage(Message msg, String reservationKey) {
        AbstractMessage message = AbstractMessage.convertMessage(msg);
        Preconditions.checkNotNull(reservationKey, "No Reservation Key.");
        message.reservationKey = reservationKey;
        synchronized (_manager) {
            try {
                _manager.getTransaction().begin();
                _manager.persist(message);
                _manager.getTransaction().commit();
                _log.info("{} from {} saved to Database", message.getClass().getSimpleName(), message.sourceNodeId);
            }
            catch (Exception e) {
                e.printStackTrace();
                _manager.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dispose() {
        if (_manager.isOpen())
            _manager.close();
    }
}
