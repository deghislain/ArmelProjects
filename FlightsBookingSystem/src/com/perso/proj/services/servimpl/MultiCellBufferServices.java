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
	//This method allow the storage of data into the cell
	public void setOneCell(Order order) {
		this.writeOneCell(order);
	}
	
	//This method implements the setOneCell method
	private void writeOneCell(Order o) {
		while (countNumEmptyCells() == 0) {
			try {
				this.wait();// if no empty cell, we wait
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (this.buffer) {
			for (int i = 0; i < buffer.length; i++) {
				if (buffer[i] == null) {
					buffer[i] = o; // we store the order into the first available empty cell
					break;
				}

			}
		}
		this.notify();
	}

	@Override
	//This method allow us to grab one order for processing from the cell
	public Order getOneCell() {
		return this.readOneCell();
	}
	
	//This method implements the readCell method
	public Order readOneCell() {
		Order myOrder = null;

		while (countNumEmptyCells() == 3) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (this.buffer) {
			for (int j = 0; j < buffer.length; j++) {
				if (buffer[j] != null) {
					myOrder = buffer[j];//we grab first available order for processing
					buffer[j] = null;//we mark the order as consumed 
					break;
				}
			}
		}
		
		this.notify();
		return myOrder;
	}

	//This method play the semaphore role
	private int countNumEmptyCells() {
		int mumEmptyCells = 0;
		Object necObj = mumEmptyCells;
		for (int i = 0; i < buffer.length; i++) {
			synchronized (necObj) {
				if (this.buffer[i] == null) {
					mumEmptyCells++;
				}
			}

		}
		return mumEmptyCells;
	}
}
