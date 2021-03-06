package data;

import acq.ICase;
import acq.ICaseWorker;
import acq.IUser;
import acq.IData;
import data.model.DataCase;
import data.model.DataCaseWorker;
import data.model.DataCitizen;
import data.model.DataUser;
import java.util.Collection;
import java.util.HashSet;
import util.Mapper;

public class DataFacade implements IData {

  /**
   * Instance of the class itself, used for singleton setup. Is set to null
   * initially, instantiated if needed though getInstance()
   */
  public static DataFacade data;

  /**
   * Instance of database
   */
  private Database database;

  /**
   * Disable normal instantiation
   */
  private DataFacade() {
    database.getInstance();
  }

  /**
   * Singleton setup for DomainFacade, returns instance of the class if none has
   * been made. If one has been made, returns that created instance
   *
   * @return Singleton instanse of DataFacade
   */
  public static DataFacade getInstance() {
    if (data == null) {
      data = new DataFacade();
    }

    return data;
  }

  /**
   * Add case
   *
   * @param c
   */
  @Override
  public void addCase(ICase c) {
    DataCase dc = (DataCase) Mapper.map(c, true);
    dc.save();
  }

  /**
   * Delete case
   *
   * @param c
   */
  public void deleteCase(ICase c) {
    // TODO
  }

  /**
   * Get collection of cases
   *
   * @return collection of cases
   */
  @Override
  public Collection<ICase> getCases() {
    Collection<ICase> cases = new HashSet<>();

    Database.getInstance().query(
            "SELECT id, citizen_id, case_worker_id, circumstance, inquiry, further_course, is_informed_about_rights, is_informed_about_duties, "
            + "practical_tasks_support, personal_care_support, grocery_support, temporary_stay, longer_stay, rehabilitation_support, "
            + "driving_support, temporary_house_offer, personal_care_offer, support_grocery_offer, longer_stay_offer, learning_offer, "
            + "rehabilitation_offer, guardianship, none_acting_guardian, curatorship, assessor, party_representative, power_of_attorney, "
            + "right_to_assessor_or_party_representative, information_saved_online "
            + "FROM cases ",
            rs -> {
              DataCase dc = new DataCase();
              dc.setId(rs.getInt(1));
              dc.setCitizen(DataCitizen.find(rs.getInt(2)));
              dc.setResponsible(DataCaseWorker.find(rs.getInt(3)));
              dc.setCircumstance(rs.getString(4));
              dc.setInquiry(rs.getString(5));
              dc.setFurtherCourse(rs.getString(6));
              dc.setInformedAboutRights(rs.getBoolean(7));
              dc.setInformedAboutDuties(rs.getBoolean(8));
              dc.setPracticalTasksSupport(rs.getBoolean(9));
              dc.setPersonalCareSupport(rs.getBoolean(10));
              dc.setGrocerySupport(rs.getBoolean(11));
              dc.setTemporaryStay(rs.getBoolean(12));
              dc.setLongerStay(rs.getBoolean(13));
              dc.setRehabilitationSupport(rs.getBoolean(14));
              dc.setDrivingSupport(rs.getBoolean(15));
              dc.setTemporaryHouseOffer(rs.getBoolean(16));
              dc.setPersonalCareOffer(rs.getBoolean(17));
              dc.setSupportGroceryOffer(rs.getBoolean(18));
              dc.setLongerStayOffer(rs.getBoolean(19));
              dc.setLearningOffer(rs.getBoolean(20));
              dc.setRehabilitationOffer(rs.getBoolean(21));
              dc.setGuardianship(rs.getBoolean(22));
              dc.setNoneActingGuardian(rs.getBoolean(23));
              dc.setCuratorship(rs.getBoolean(24));
              dc.setAssessor(rs.getBoolean(25));
              dc.setPartyRepresentative(rs.getBoolean(26));
              dc.setPowerOfAttorney(rs.getBoolean(27));
              dc.setRightToAssessorOrPartyRepresentative(rs.getBoolean(28));
              dc.setInformationSavedOnline(rs.getBoolean(29));

              cases.add(dc);
            });

    return cases;
  }

  /**
   * Find a user from its username
   *
   * @param username
   * @return case worker
   */
  @Override
  public IUser findUser(String username) {
    return DataUser.where("username", username);
  }

  /**
   * Find a caseworker from its user_id
   *
   * @param id
   * @return CaseWorker
   */
  @Override
  public ICaseWorker findCaseWorker(int id) {
    return DataCaseWorker.where("user_id", "" + id);
  }
}
