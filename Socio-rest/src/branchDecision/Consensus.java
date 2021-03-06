/**
 */
package branchDecision;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import socioProjects.BranchGroup;
import socioProjects.Project;
import socioProjects.User;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Consensus</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link branchDecision.Consensus#getConsensusRequired <em>Consensus Required</em>}</li>
 *   <li>{@link branchDecision.Consensus#getRounds <em>Rounds</em>}</li>
 *   <li>{@link branchDecision.Consensus#getUsers <em>Users</em>}</li>
 * </ul>
 *
 * @see branchDecision.BranchDecisionPackage#getConsensus()
 * @model
 * @generated
 */
public interface Consensus extends Decision {
	/**
	 * Returns the value of the '<em><b>Consensus Required</b></em>' attribute.
	 * The default value is <code>"0.75"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consensus Required</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consensus Required</em>' attribute.
	 * @see #setConsensusRequired(double)
	 * @see branchDecision.BranchDecisionPackage#getConsensus_ConsensusRequired()
	 * @model default="0.75" required="true"
	 * @generated
	 */
	double getConsensusRequired();

	/**
	 * Sets the value of the '{@link branchDecision.Consensus#getConsensusRequired <em>Consensus Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consensus Required</em>' attribute.
	 * @see #getConsensusRequired()
	 * @generated
	 */
	void setConsensusRequired(double value);

	/**
	 * Returns the value of the '<em><b>Rounds</b></em>' containment reference list.
	 * The list contents are of type {@link branchDecision.Round}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rounds</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rounds</em>' containment reference list.
	 * @see branchDecision.BranchDecisionPackage#getConsensus_Rounds()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Round> getRounds();

	/**
	 * Returns the value of the '<em><b>Users</b></em>' reference list.
	 * The list contents are of type {@link socioProjects.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users</em>' reference list.
	 * @see branchDecision.BranchDecisionPackage#getConsensus_Users()
	 * @model lower="2"
	 * @generated
	 */
	EList<User> getUsers();

	void calculateConsensus() throws Exception;

	List<User> getUsersNoVoted();
	double getConsensusActualMeasure();
	
	double getConsensusMaxMeasure();
	Project getConsensusFirstOption();

	boolean setPreference(PreferenceOrdering pref);

	List<Project> getOrder();

	Map<User, Double> getUsersReVote(int rNum);

	void addRound(Round r);
	BranchGroup getBranchGroup();
	EList<Order> getConsensusOrder();
	Round getLastRound();

	boolean isRevoteCandidate(User u);


} // Consensus
