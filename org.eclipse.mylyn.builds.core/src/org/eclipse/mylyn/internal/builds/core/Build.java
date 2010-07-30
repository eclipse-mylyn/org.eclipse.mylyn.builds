/**
 * <copyright>
 * </copyright>
 *
 * $Id: Build.java,v 1.1 2010/07/08 02:02:34 spingel Exp $
 */
package org.eclipse.mylyn.internal.builds.core;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.mylyn.builds.core.BuildState;
import org.eclipse.mylyn.builds.core.BuildStatus;
import org.eclipse.mylyn.builds.core.IArtifact;
import org.eclipse.mylyn.builds.core.IBuild;
import org.eclipse.mylyn.builds.core.IChangeSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Build</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getBuild()
 * @model kind="class" superTypes="org.eclipse.mylyn.internal.builds.core.IBuild"
 * @generated
 */
public class Build extends EObjectImpl implements EObject, IBuild {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuildNumber() <em>Build Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int BUILD_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBuildNumber() <em>Build Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildNumber()
	 * @generated
	 * @ordered
	 */
	protected int buildNumber = BUILD_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIMESTAMP_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected long timestamp = TIMESTAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected static final long DURATION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected long duration = DURATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final BuildState STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected BuildState state = STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final BuildStatus STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected BuildStatus status = STATUS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArtifacts() <em>Artifacts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList<IArtifact> artifacts;

	/**
	 * The cached value of the '{@link #getChangeSet() <em>Change Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeSet()
	 * @generated
	 * @ordered
	 */
	protected IChangeSet changeSet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Build() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BuildPackage.Literals.BUILD;
	}

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_Id()
	 * @model
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__ID, oldId, id));
	}

	/**
	 * Returns the value of the '<em><b>Build Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Number</em>' attribute.
	 * @see #setBuildNumber(int)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_BuildNumber()
	 * @model
	 * @generated
	 */
	public int getBuildNumber() {
		return buildNumber;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getBuildNumber <em>Build Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Number</em>' attribute.
	 * @see #getBuildNumber()
	 * @generated
	 */
	public void setBuildNumber(int newBuildNumber) {
		int oldBuildNumber = buildNumber;
		buildNumber = newBuildNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__BUILD_NUMBER, oldBuildNumber,
					buildNumber));
	}

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_Timestamp()
	 * @model
	 * @generated
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	public void setTimestamp(long newTimestamp) {
		long oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__TIMESTAMP, oldTimestamp,
					timestamp));
	}

	/**
	 * Returns the value of the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Duration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(long)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_Duration()
	 * @model
	 * @generated
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getDuration <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated
	 */
	public void setDuration(long newDuration) {
		long oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__DURATION, oldDuration, duration));
	}

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_DisplayName()
	 * @model
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__DISPLAY_NAME, oldDisplayName,
					displayName));
	}

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see #setState(BuildState)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_State()
	 * @model dataType="org.eclipse.mylyn.internal.builds.core.BuildState"
	 * @generated
	 */
	public BuildState getState() {
		return state;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see #getState()
	 * @generated
	 */
	public void setState(BuildState newState) {
		BuildState oldState = state;
		state = newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__STATE, oldState, state));
	}

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(BuildStatus)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_Status()
	 * @model dataType="org.eclipse.mylyn.internal.builds.core.BuildStatus"
	 * @generated
	 */
	public BuildStatus getStatus() {
		return status;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	public void setStatus(BuildStatus newStatus) {
		BuildStatus oldStatus = status;
		status = newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__STATUS, oldStatus, status));
	}

	/**
	 * Returns the value of the '<em><b>Artifacts</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.builds.core.IArtifact}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifacts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifacts</em>' reference list.
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_Artifacts()
	 * @model type="org.eclipse.mylyn.internal.builds.core.IArtifact"
	 * @generated
	 */
	public EList<IArtifact> getArtifacts() {
		if (artifacts == null) {
			artifacts = new EObjectResolvingEList<IArtifact>(IArtifact.class, this, BuildPackage.BUILD__ARTIFACTS);
		}
		return artifacts;
	}

	/**
	 * Returns the value of the '<em><b>Change Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Set</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Set</em>' reference.
	 * @see #setChangeSet(IChangeSet)
	 * @see org.eclipse.mylyn.internal.builds.core.BuildPackage#getIBuild_ChangeSet()
	 * @model type="org.eclipse.mylyn.internal.builds.core.IChangeSet"
	 * @generated
	 */
	public IChangeSet getChangeSet() {
		if (changeSet != null && ((EObject) changeSet).eIsProxy()) {
			InternalEObject oldChangeSet = (InternalEObject) changeSet;
			changeSet = (IChangeSet) eResolveProxy(oldChangeSet);
			if (changeSet != oldChangeSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BuildPackage.BUILD__CHANGE_SET,
							oldChangeSet, changeSet));
			}
		}
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IChangeSet basicGetChangeSet() {
		return changeSet;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.internal.builds.core.Build#getChangeSet <em>Change Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change Set</em>' reference.
	 * @see #getChangeSet()
	 * @generated
	 */
	public void setChangeSet(IChangeSet newChangeSet) {
		IChangeSet oldChangeSet = changeSet;
		changeSet = newChangeSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildPackage.BUILD__CHANGE_SET, oldChangeSet,
					changeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BuildPackage.BUILD__ID:
			return getId();
		case BuildPackage.BUILD__BUILD_NUMBER:
			return getBuildNumber();
		case BuildPackage.BUILD__TIMESTAMP:
			return getTimestamp();
		case BuildPackage.BUILD__DURATION:
			return getDuration();
		case BuildPackage.BUILD__DISPLAY_NAME:
			return getDisplayName();
		case BuildPackage.BUILD__STATE:
			return getState();
		case BuildPackage.BUILD__STATUS:
			return getStatus();
		case BuildPackage.BUILD__ARTIFACTS:
			return getArtifacts();
		case BuildPackage.BUILD__CHANGE_SET:
			if (resolve)
				return getChangeSet();
			return basicGetChangeSet();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BuildPackage.BUILD__ID:
			setId((String) newValue);
			return;
		case BuildPackage.BUILD__BUILD_NUMBER:
			setBuildNumber((Integer) newValue);
			return;
		case BuildPackage.BUILD__TIMESTAMP:
			setTimestamp((Long) newValue);
			return;
		case BuildPackage.BUILD__DURATION:
			setDuration((Long) newValue);
			return;
		case BuildPackage.BUILD__DISPLAY_NAME:
			setDisplayName((String) newValue);
			return;
		case BuildPackage.BUILD__STATE:
			setState((BuildState) newValue);
			return;
		case BuildPackage.BUILD__STATUS:
			setStatus((BuildStatus) newValue);
			return;
		case BuildPackage.BUILD__ARTIFACTS:
			getArtifacts().clear();
			getArtifacts().addAll((Collection<? extends IArtifact>) newValue);
			return;
		case BuildPackage.BUILD__CHANGE_SET:
			setChangeSet((IChangeSet) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case BuildPackage.BUILD__ID:
			setId(ID_EDEFAULT);
			return;
		case BuildPackage.BUILD__BUILD_NUMBER:
			setBuildNumber(BUILD_NUMBER_EDEFAULT);
			return;
		case BuildPackage.BUILD__TIMESTAMP:
			setTimestamp(TIMESTAMP_EDEFAULT);
			return;
		case BuildPackage.BUILD__DURATION:
			setDuration(DURATION_EDEFAULT);
			return;
		case BuildPackage.BUILD__DISPLAY_NAME:
			setDisplayName(DISPLAY_NAME_EDEFAULT);
			return;
		case BuildPackage.BUILD__STATE:
			setState(STATE_EDEFAULT);
			return;
		case BuildPackage.BUILD__STATUS:
			setStatus(STATUS_EDEFAULT);
			return;
		case BuildPackage.BUILD__ARTIFACTS:
			getArtifacts().clear();
			return;
		case BuildPackage.BUILD__CHANGE_SET:
			setChangeSet((IChangeSet) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case BuildPackage.BUILD__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case BuildPackage.BUILD__BUILD_NUMBER:
			return buildNumber != BUILD_NUMBER_EDEFAULT;
		case BuildPackage.BUILD__TIMESTAMP:
			return timestamp != TIMESTAMP_EDEFAULT;
		case BuildPackage.BUILD__DURATION:
			return duration != DURATION_EDEFAULT;
		case BuildPackage.BUILD__DISPLAY_NAME:
			return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
		case BuildPackage.BUILD__STATE:
			return STATE_EDEFAULT == null ? state != null : !STATE_EDEFAULT.equals(state);
		case BuildPackage.BUILD__STATUS:
			return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT.equals(status);
		case BuildPackage.BUILD__ARTIFACTS:
			return artifacts != null && !artifacts.isEmpty();
		case BuildPackage.BUILD__CHANGE_SET:
			return changeSet != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", buildNumber: ");
		result.append(buildNumber);
		result.append(", timestamp: ");
		result.append(timestamp);
		result.append(", duration: ");
		result.append(duration);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", state: ");
		result.append(state);
		result.append(", status: ");
		result.append(status);
		result.append(')');
		return result.toString();
	}

} // Build