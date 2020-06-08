/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.entities.Order;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;


/**
 * @author deghislain
 *
 */
public class MultiCellBufferServices implements IMultiCellBufferServices {
	private Order[] buffer;

	public MultiCellBufferServices() {
		this.buffer = new Order[] { null, null, null };
	}

	@Override
	// This method allow the storage of data into the cell
	public void setOneCell(Order order) {
		this.writeOneCell(order);
	}

	// This method implements the setOneCell method
	private void writeOneCell(Order o) {
		synchronized (this.buffer) {
			while (countNumEmptyCells() == 0) {
				try {
					this.buffer.wait();// if no empty cell, we wait
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < this.buffer.length; i++) {
				if (this.buffer[i] == null) {
					this.buffer[i] = o; // we store the order into the first available empty cell
					break;
				}

			}
			this.buffer.notifyAll();
		}

	}

	@Override
	// This method allow us to grab one order for processing from the cell
	public Order getOneCell() {
		return this.readOneCell();
	}

	// This method implements the readCell method
	public Order readOneCell() {
		Order myOrder = null;
		synchronized (this.buffer) {
			while (countNumEmptyCells() == 3) {
				try {
					this.buffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int j = 0; j < this.buffer.length; j++) {
				if (this.buffer[j] != null) {
					myOrder = this.buffer[j];// we grab first available order for processing
					this.buffer[j] = null;// we mark the order as consumed
					break;
				}
			}
			this.buffer.notifyAll();
		}

		return myOrder;
	}

	// This method play the semaphore role
	private int countNumEmptyCells() {
		int mumEmptyCells = 0;
		for (int i = 0; i < this.buffer.length; i++) {
				if (this.buffer[i] == null) {
					mumEmptyCells++;
				}

		}
		return mumEmptyCells;
	}
}
