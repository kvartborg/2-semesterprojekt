package acq;

import java.util.GregorianCalendar;

public interface IEffort {

  /**
   * Get total price
   * @return total price of Effort
   */
  int getTotalPrice();

  /**
   * Get start date
   * @return starting date of effort
   */
  GregorianCalendar getStartDate();

  /**
   * Get end date
   * @return end date of Effort
   */
  GregorianCalendar getEndDate();

  /**
   * Get responsible
   * @return company responsible for the effort
   */
  ICompany getResponsible();

}
