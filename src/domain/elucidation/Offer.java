package domain.elucidation;

import acq.IOffer;

/**
 *
 * Offer class that implements IOffer.
 */
public class Offer implements IOffer {

  /**
   * Name of offer
   */
  private String name;

  /**
   * Price of offer
   */
  private int price;

  /**
   * Description of offer
   */
  private String description;

  public Offer() {
  }

  /**
   * Constructor for offers
   *
   * @param name
   * @param price
   * @param description
   */
  public Offer(String name, int price, String description) {
    this.name = name;
    this.price = price;
    this.description = description;
  }

  /**
   * Get name
   *
   * @return name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Set name
   *
   * @param name
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get price
   *
   * @return price
   */
  @Override
  public int getPrice() {
    return price;
  }

  /**
   * Set price
   *
   * @param price
   */
  @Override
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * Get description
   *
   * @return description
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Set description
   *
   * @param description
   */
  @Override
  public void setDescription(String description) {
    this.description = description;
  }
}
