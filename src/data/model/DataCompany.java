package data.model;

import acq.IAddress;
import acq.ICompany;

public class DataCompany implements ICompany {

  /**
   * Name of company
   */
  private String name;

  /**
   * Address of company
   */
  private IAddress address;

  /**
   * Create new instance of company
   * @param name
   * @param address
   */
  public DataCompany(String name, IAddress address) {
    this.name = name;
    this.address = address;
  }

  /**
   * Get name
   * @return name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Get address
   * @return address
   */
  @Override
  public IAddress getAddress() {
    return this.address;
  }

}
