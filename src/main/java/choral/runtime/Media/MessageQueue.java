/*
 * Copyright (C) 2019 by Saverio Giallorenzo <saverio.giallorenzo@gmail.com>
 * Copyright (C) 2019 by Fabrizio Montesi <famontesi@gmail.com>
 * Copyright (C) 2019 by Marco Peressotti <marco.peressotti@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Library General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package choral.runtime.Media;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MessageQueue {

	private final LinkedList< Object > sendQueue = new LinkedList<>();
	private final LinkedList< CompletableFuture< Object > > recvQueue = new LinkedList<>();

	public void send( Object message ) {
		synchronized( this ) {
			if ( recvQueue.isEmpty() ){
				sendQueue.add( message );
			} else {
				CompletableFuture< Object > future = recvQueue.removeFirst();
				future.complete( message );
			}
		}
	}

	public < T > T recv() throws ExecutionException, InterruptedException {
		CompletableFuture< Object > future = new CompletableFuture<>();
		synchronized ( this ){
			if( sendQueue.isEmpty() ){
				recvQueue.add( future );
			} else {
				future.complete( sendQueue.removeFirst() );
			}
		}
		return (T) future.get();
	}
}
