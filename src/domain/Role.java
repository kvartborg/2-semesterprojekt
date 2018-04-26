package domain;

import acq.IRole;

public class Role implements IRole {

  /**
   * Name of role
   */
  private String name;

  /**
   * can the role open case
   */
  private boolean canOpenCase;

  /**
   * Can the role view case
   */
  private boolean canViewCase;

  /**
   * can the role evaluate case
   */
  private boolean canEvaluateCase;

  /**
   * can close case
   */
  private boolean canCloseCase;

  /**
   * Can create case
   */
  private boolean canCreateCase;

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
   * Is Case open
   *
   * @return canOpenCase
   */
  @Override
  public boolean canOpenCase() {
    return canOpenCase;
  }

  /**
   * Can role view Case
   *
   * @return canViewCace
   */
  @Override
  public boolean canViewCase() {
    return canViewCase;
  }

  /**
   * Can role evaluate Case
   *
   * @return canEvaluateCase
   */
  @Override
  public boolean canEvaluateCase() {
    return canEvaluateCase;
  }

  /**
   * Can role close Case
   *
   * @return canCloseCase
   */
  @Override
  public boolean canCloseCase() {
    return canCloseCase;
  }

  /**
   * Set name
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set whether a role can open a case
   *
   * @param permission
   */
  @Override
  public void setCanOpenCase(boolean permission) {
    this.canOpenCase = permission;
  }

  /**
   * Set whether a role can view a case
   *
   * @param permission
   */
  @Override
  public void setCanViewCase(boolean permission) {
    this.canViewCase = permission;
  }

  /**
   * Set whether a role can evaluate a case
   *
   * @param permission
   */
  @Override
  public void setCanEvaluateCase(boolean permission) {
    this.canEvaluateCase = permission;
  }

  /**
   * Set whether a role can close a case
   *
   * @param permission
   */
  @Override
  public void setCanCloseCase(boolean permission) {
    this.canCloseCase = permission;
  }

  /**
   * Whether a role can create a case
   *
   * @return canCreateCase
   */
  @Override
  public boolean canCreateCase() {
    return this.canCreateCase;
  }

  /**
   * Set whether a role can create a case
   *
   * @param permission
   */
  @Override
  public void setCanCreateCase(boolean permission) {
    this.canCreateCase = permission;
  }
}
