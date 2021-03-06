/**
 */
package socioProjects;

import branchDecision.Decision;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import es.uam.app.actions.Add;
import es.uam.app.actions.Delete;
import es.uam.app.actions.Update;
import es.uam.app.main.exceptions.FatalException;
import projectHistory.Action;
import projectHistory.History;
import projectHistory.Msg;
import removeLog.Root;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link socioProjects.Project#getName <em>Name</em>}</li>
 *   <li>{@link socioProjects.Project#isOpen <em>Open</em>}</li>
 *   <li>{@link socioProjects.Project#getHistory <em>History</em>}</li>
 *   <li>{@link socioProjects.Project#getRemove <em>Remove</em>}</li>
 *   <li>{@link socioProjects.Project#getModel <em>Model</em>}</li>
 *   <li>{@link socioProjects.Project#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link socioProjects.Project#getAdmin <em>Admin</em>}</li>
 *   <li>{@link socioProjects.Project#isBranchIsLocking <em>Branch Is Locking</em>}</li>
 *   <li>{@link socioProjects.Project#getId <em>Id</em>}</li>
 *   <li>{@link socioProjects.Project#getBranchGroup <em>Branch Group</em>}</li>
 *   <li>{@link socioProjects.Project#isBranch <em>Branch</em>}</li>
 *   <li>{@link socioProjects.Project#getOpenBranchs <em>Open Branchs</em>}</li>
 *   <li>{@link socioProjects.Project#getCloseBranchs <em>Close Branchs</em>}</li>
 * </ul>
 *
 * @see socioProjects.SocioProjectsPackage#getProject()
 * @model abstract="true"
 * @generated
 */
public interface Project extends EObject {
	
		
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see socioProjects.SocioProjectsPackage#getProject_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Open</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open</em>' attribute.
	 * @see #setOpen(boolean)
	 * @see socioProjects.SocioProjectsPackage#getProject_Open()
	 * @model default="true"
	 * @generated
	 */
	boolean isOpen();

	/**
	 * Sets the value of the '{@link socioProjects.Project#isOpen <em>Open</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Open</em>' attribute.
	 * @see #isOpen()
	 * @generated
	 */
	void setOpen(boolean value);

	/**
	 * Returns the value of the '<em><b>History</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>History</em>' containment reference.
	 * @see #setHistory(History)
	 * @see socioProjects.SocioProjectsPackage#getProject_History()
	 * @model containment="true" required="true"
	 * @generated
	 */
	History getHistory();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getHistory <em>History</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>History</em>' containment reference.
	 * @see #getHistory()
	 * @generated
	 */
	void setHistory(History value);

	/**
	 * Returns the value of the '<em><b>Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove</em>' containment reference.
	 * @see #setRemove(Root)
	 * @see socioProjects.SocioProjectsPackage#getProject_Remove()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Root getRemove();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getRemove <em>Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove</em>' containment reference.
	 * @see #getRemove()
	 * @generated
	 */
	void setRemove(Root value);

	/**
	 * Returns the value of the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' reference.
	 * @see #setModel(EObject)
	 * @see socioProjects.SocioProjectsPackage#getProject_Model()
	 * @model required="true"
	 * @generated
	 */
	EObject getModel();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getModel <em>Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(EObject value);

	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute.
	 * The literals are from the enumeration {@link socioProjects.Visibility}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visibility</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see socioProjects.Visibility
	 * @see #setVisibility(Visibility)
	 * @see socioProjects.SocioProjectsPackage#getProject_Visibility()
	 * @model required="true"
	 * @generated
	 */
	Visibility getVisibility();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility</em>' attribute.
	 * @see socioProjects.Visibility
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(Visibility value);

	/**
	 * Returns the value of the '<em><b>Admin</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link socioProjects.User#getOwnProjects <em>Own Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Admin</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Admin</em>' reference.
	 * @see #setAdmin(User)
	 * @see socioProjects.SocioProjectsPackage#getProject_Admin()
	 * @see socioProjects.User#getOwnProjects
	 * @model opposite="ownProjects" required="true"
	 * @generated
	 */
	User getAdmin();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getAdmin <em>Admin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Admin</em>' reference.
	 * @see #getAdmin()
	 * @generated
	 */
	void setAdmin(User value);

	/**
	 * Returns the value of the '<em><b>Branch Is Locking</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch Is Locking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch Is Locking</em>' attribute.
	 * @see #setBranchIsLocking(boolean)
	 * @see socioProjects.SocioProjectsPackage#getProject_BranchIsLocking()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isBranchIsLocking();

	/**
	 * Sets the value of the '{@link socioProjects.Project#isBranchIsLocking <em>Branch Is Locking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch Is Locking</em>' attribute.
	 * @see #isBranchIsLocking()
	 * @generated
	 */
	void setBranchIsLocking(boolean value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see socioProjects.SocioProjectsPackage#getProject_Id()
	 * @model required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Returns the value of the '<em><b>Branch Group</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch Group</em>' attribute.
	 * @see #setBranchGroup(String)
	 * @see socioProjects.SocioProjectsPackage#getProject_BranchGroup()
	 * @model default="" required="true"
	 * @generated
	 */
	String getBranchGroup();

	/**
	 * Sets the value of the '{@link socioProjects.Project#getBranchGroup <em>Branch Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch Group</em>' attribute.
	 * @see #getBranchGroup()
	 * @generated
	 */
	void setBranchGroup(String value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(boolean)
	 * @see socioProjects.SocioProjectsPackage#getProject_Branch()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isBranch();

	/**
	 * Sets the value of the '{@link socioProjects.Project#isBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #isBranch()
	 * @generated
	 */
	void setBranch(boolean value);

	/**
	 * Returns the value of the '<em><b>Open Branchs</b></em>' reference list.
	 * The list contents are of type {@link socioProjects.Project}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Branchs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open Branchs</em>' reference list.
	 * @see socioProjects.SocioProjectsPackage#getProject_OpenBranchs()
	 * @model
	 * @generated
	 */
	EList<Project> getOpenBranchs();

	/**
	 * Returns the value of the '<em><b>Close Branchs</b></em>' containment reference list.
	 * The list contents are of type {@link branchDecision.Decision}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Close Branchs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Close Branchs</em>' containment reference list.
	 * @see socioProjects.SocioProjectsPackage#getProject_CloseBranchs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Decision> getCloseBranchs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getPath();
	
	String getFilePath();
	
	String getFileExtension();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws Exception 
	 * @model sentenceRequired="true"
	 * @generated NOT
	 */
	List<Action> parseSentence(String sentence) throws Exception;
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @throws Exception 
	 * @model msgRequired="true"
	 * @generated NOT
	 */
	File execute(Msg msg) throws Exception;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @throws Exception 
	 * @model
	 * @generated NOT
	 */
	File undo() throws Exception;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @throws Exception 
	 * @model
	 * @generated NOT
	 */
	File redo() throws Exception;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<Msg> getHistoryMsg();
	File getProjectHistory();
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dateRequired="true" orderRequired="true"
	 * @generated NOT
	 */
	List<Msg> getHistoryMsg(Date date, int order);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model startRequired="true" endRequired="true" orderRequired="true"
	 * @generated NOT
	 */
	List<Msg> getHistoryMsg(Date start, Date end, int order);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nickRequired="true"
	 * @generated NOT
	 */
	List<Msg> getHistoryForUser(User u);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model elementRequired="true"
	 * @generated NOT
	 */
	List<Msg> getHistoryForElement(String element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model actionRequired="true"
	 * @generated NOT
	 */
	List<Msg> getHistoryForAction(String action);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @throws IOException 
	 * @model kind="operation"
	 * @generated NOT
	 */
	File getStatisticsUserMsg() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @throws IOException 
	 * @model nickRequired="true"
	 * @generated NOT
	 */
	File getStatisticsUserMsg(User u) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model kind="operation"
	 * @generated NOT
	 */
	File getStatisticsUserAction() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model nickRequired="true"
	 * @generated NOT
	 */
	File getStatisticsUserAction(User u) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model kind="operation"
	 * @generated NOT
	 */
	File getStatisticsUserMsgAbs() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model kind="operation"
	 * @generated NOT
	 */
	File getStatisticsUserActionAbs() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model kind="operation"
	 * @generated NOT
	 */
	File getStatisticsActions() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws IOException 
	 * @model
	 * @generated NOT
	 */
	File percentOfAuthorship() throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	String validate();
	void initialize() throws FatalException;
	
	
	Date getCreateDate();
	
	String getProjectData();

	void remove();

	Add<? extends Project> createAddAction(EObject element);

	Delete<? extends Project> createDeleteAction(EObject element);

	Update<? extends Project> createUpdateAction(EObject element, EObject new_, EObject old);
	void save();
	void setFather(Project father);
	Project getFather();

	Project getOpenBranch(String branchName);

	void createBranch(Project p) throws Exception;

	Decision getDecision(String text);
	Project getCloseBranch(String text);
	
	boolean isLocked();

	String getCompleteName();

	File getPng(List<Action> arrayList, boolean sort);
	File getPng(List<Action> actions);

	void removeBranch(Project project);
	
	/*void startDecision(Decision d, List<Project> branchsGroup);*/
	List<Project> startDecision(Decision d, String branchsGroup);

	String getAllOpenBranchsGroupString();
	Map<String, List<Project>> getAllOpenBranchsGroup();

	List<Project> getOpenBranchGroup(String text);
	
	List<Action> makeDecision(Decision d, Project branch) throws Exception;
	

} // Project
