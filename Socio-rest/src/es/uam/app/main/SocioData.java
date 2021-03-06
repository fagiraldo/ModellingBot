package es.uam.app.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.socio.client.command.SaveFileServer;

import branchDecision.AdminChoice;
import branchDecision.Consensus;
import branchDecision.Decision;
import branchDecision.Order;
import branchDecision.PreferenceOrdering;
import branchDecision.Round;
import branchDecision.impl.BranchDecisionFactoryImpl;
import es.uam.app.consensus.ConsensusTimeOut;
import es.uam.app.main.commands.DataFormat;
import es.uam.app.main.exceptions.FatalException;
import es.uam.app.main.exceptions.InternalException;
import es.uam.app.parser.rules.ExtractionRule;
import es.uam.app.parser.rules.MetemodelRule;
import es.uam.app.words.WordNet;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import projectHistory.Action;
import projectHistory.CreateMsg;
import projectHistory.Msg;
import projectHistory.impl.ProjectHistoryFactoryImpl;
import removeLog.impl.RemoveLogFactoryImpl;
import socioProjects.Access;
import socioProjects.BranchGroup;
import socioProjects.Contribution;
import socioProjects.MetamodelProject;
import socioProjects.Project;
import socioProjects.SocioApp;
import socioProjects.User;
import socioProjects.Visibility;
import socioProjects.impl.MetamodelProjectImpl;
import socioProjects.impl.ProjectImpl;
import socioProjects.impl.SocioProjectsFactoryImpl;
import socioProjects.impl.SocioProjectsPackageImpl;

public class SocioData implements DataFormat {

	private static ResourceSet resourceSet = null;
	private Resource resource;
	private final static String PROJECTS_PATH = "/projects";
	private static String PATH = "";
	protected final static String FILENAME = "socioData.xmi";
	private SocioApp socioApp;
	private Map<String, ConsensusTimeOut> timesOuts = new HashMap<String, ConsensusTimeOut>();

	private Map<String, List<Project>> channels_projects = new HashMap<>();
	private Map<String, Map<Project, List<Msg>>> updated = new HashMap<>();
	private Map<String, List<Consensus>> poll = new HashMap<>();
	private Map<String, List<Consensus>> pollEnd = new HashMap<>();
	public enum ProjectType {
		METAMODEL, MODEL
	}

	private static SocioData socioData = null;

	/**
	 * Genera el resourceSet para despues obtener el resource del ecore.
	 * 
	 * @return el conjunto de todos los resources
	 */

	public static ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			// Especificamos la extension del fichero (para todos los ficheros),
			// y indicamos que
			// es un XMI.
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			resourceSet.getPackageRegistry().put("socioProjects", SocioProjectsPackageImpl.eINSTANCE);
		}
		return resourceSet;
	}

	/**
	 * Crear la clase singleton SocioData, para ello es necesario usar el contexto
	 * de la aplicación
	 * 
	 * @param context
	 *            Contexto de la aplicacion
	 * @return La instancia de la clase singleton SocioData
	 */
	public static SocioData getSocioData(ServletContext context) throws Exception {
		if (socioData == null) {
			PATH = context.getInitParameter("project.files") + PROJECTS_PATH;
			socioData = new SocioData(PATH);
			ini();
			WordNet.ini(context);
		}
		return socioData;
	}

	private static void ini() throws Exception {

		// Registrando Extraction Rules
		List<Class<? extends ExtractionRule<MetamodelProject>>> rules = new ArrayList<>();
		new FastClasspathScanner(ExtractionRule.class.getPackage().getName())
				.matchSubclassesOf(MetemodelRule.class, rules::add).scan();
		try {

			for (Class<? extends ExtractionRule<MetamodelProject>> r : rules) {
				MetamodelProjectImpl.registerRule(r);
			}

		} catch (NoSuchMethodException | SecurityException e) {
			throw e;
		}

	}

	public static String getPath() {
		return PATH;
	}

	private SocioData(String path) throws FatalException {
		File file = new File(path + "/" + FILENAME);
		if (file.exists()) {
			try {
				resource = getResourceSet().getResource(URI.createURI(path + "/" + FILENAME), true);
				socioApp = (SocioApp) resource.getContents().get(0);
				loadProjects();
				loadChannels();
			} catch (Exception e) {
				e.printStackTrace();
				throw new FatalException("In class " + this.getClass().getName() + ": the file " + path + "/" + FILENAME
						+ " can be opened: ");
			}
		} else {
			this.resource = getResourceSet().createResource(URI.createURI(path + "/" + FILENAME));
			socioApp = SocioProjectsFactoryImpl.eINSTANCE.createSocioApp();
			resource.getContents().add(socioApp);
		}

		save(null);
	}

	private void loadChannels() {
		List<User> users = getUsers();
		for (User u : users) {
			register(u.getChannel());
			for (Project p : u.getOwnProjects()) {
				addProjectToChannel(u.getChannel(), p);
			}
			for (Contribution c : u.getContributions()) {
				addProjectToChannel(u.getChannel(), c.getProject());
			}
		}

	}

	public void loadProjects() throws Exception {
		long lastid = Long.MIN_VALUE;
		List<Project> remove = new ArrayList<>();
		for (Project p : socioApp.getProjects()) {
			try {
				p.initialize();
				if (lastid < p.getId()) {
					lastid = p.getId();
				}
			} catch (FatalException e) {
				remove.add(p);

			}
		}
		for (Project p : remove) {
			removeProject(p);
		}
		ProjectImpl.setLastId(lastid);
		save(null);
	}

	public void save(Project p) {
		try {
			/*
			 * Save the resource
			 */
			if (p != null) {
				p.save();
			}
			resource.save(null);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*-------------------------------------------------------Users looking for methods--------------------------------------------------------------------------------*/
	public List<User> getUsers() {
		return socioApp.getUsers();
	}

	public User getUser(String nick, String channel) throws InternalException {
		List<User> users = socioApp.getUsers();
		try {
			Long id = Long.parseLong(nick);
			return getUser(id, channel);

		} catch (NumberFormatException e) {
			for (User u : users) {
				if (u.getNick().equalsIgnoreCase(nick) && u.getChannel().equalsIgnoreCase(channel)) {
					return u;
				}
			}
		}
		return null;

	}

	public User getUser(long id, String channel) {
		List<User> users = socioApp.getUsers();
		for (User u : users) {
			if (u.getId() == id && u.getChannel().equalsIgnoreCase(channel)) {
				return u;
			}
		}
		return null;
	}

	public User getUser(long id) {
		List<User> users = socioApp.getUsers();
		List<User> usersValid = new ArrayList<>();
		for (User u : users) {
			if (u.getId() == id) {
				usersValid.add(u);
			}
		}
		if (usersValid.size() > 1) {
			int numAleat = (int) Math.floor(Math.random() * usersValid.size() + 1);
			return usersValid.get(numAleat);
		} else if (usersValid.size() < 1) {
			return null;
		} else {
			return usersValid.get(0);
		}
	}

	public User getUser(String nick) {
		List<User> users = socioApp.getUsers();
		List<User> usersValid = new ArrayList<>();
		for (User u : users) {
			if (u.getNick().equalsIgnoreCase(nick)) {
				usersValid.add(u);
			}
		}
		if (usersValid.size() > 1) {
			int numAleat = (int) Math.floor(Math.random() * usersValid.size() + 1);
			return usersValid.get(numAleat);
		} else if (usersValid.size() < 1) {
			return null;
		} else {
			return usersValid.get(0);
		}
	}

	public List<User> getAllUser(Long id) {
		List<User> users = socioApp.getUsers();
		List<User> usersValid = new ArrayList<>();
		for (User u : users) {
			if (u.getId() == id) {
				usersValid.add(u);
			}
		}
		return usersValid;

	}

	public List<User> getAllUser(String nick) {
		List<User> users = socioApp.getUsers();
		List<User> usersValid = new ArrayList<>();
		for (User u : users) {
			if (u.getNick().equalsIgnoreCase(nick)) {
				usersValid.add(u);
			}
		}
		return usersValid;
	}

	public List<User> getUsers(Project p) {
		List<User> users = getUsers();
		List<User> ret = new ArrayList<User>();
		if (p.getVisibility().equals(Visibility.PRIVATE)) {
			for (User u : users) {
				if ((u.canEdit(p) || u.canRead(p)) && !u.isAdmin(p)) {
					ret.add(u);
				}
			}
		} else if (p.getVisibility().equals(Visibility.PROTECTED)) {
			for (User u : users) {
				if (u.canEdit(p) && !u.isAdmin(p)) {
					ret.add(u);
				}
			}
		}
		return ret;

	}

	/*-----------------------------------------------------Project looking for methods-------------------------------------------------------------------------------------*/
	public List<Project> getProjects() {
		return socioApp.getProjects();
	}

	public List<Project> getProjects(User user) {
		List<Project> ret = new ArrayList<>();
		for (Project p : user.getOwnProjects()) {
			if (p.isOpen()) {
				ret.add(p);
			}
		}
		return ret;
	}

	public Project getProject(String name, User u) {
		List<Project> projects = socioApp.getProjects();
		for (Project p : projects) {
			if (p.getName().equalsIgnoreCase(name) && p.getAdmin().equals(u)) {
				return p;
			}
		}
		return null;
	}

	public Project getProject(long id) {
		List<Project> projects = socioApp.getProjects();
		for (Project p : projects) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public List<Project> getProjectsForVisibility(Visibility v) {
		List<Project> projects = getProjects();
		List<Project> ret = new ArrayList<>();

		for (Project p : projects) {
			if (p.getVisibility().equals(v)) {
				ret.add(p);
			}
		}
		return ret;
	}

	public List<Project> getProjectsForUserCanWrite(User user) {
		List<Contribution> contributionProjects = user.getContributions();
		List<Project> write = new ArrayList<>();
		for (Contribution c : contributionProjects) {
			if (c.getAccess().equals(Access.EDIT)) {
				write.add(c.getProject());
			}
		}
		return write;
	}

	public List<Project> getProjectsForUserCanRead(User user) {
		List<Contribution> contributionProjects = user.getContributions();
		List<Project> read = new ArrayList<>();
		for (Contribution c : contributionProjects) {
			if (c.getAccess().equals(Access.READ)) {
				read.add(c.getProject());
			}
		}
		return read;
	}

	/*-----------------------------------------------------------------Do, undo and redo methods--------------------------------------------------------------------------*/

	public File execute(Project p, Msg msg) throws Exception {
		if (!p.isOpen() && msg.hasText()) {
			throw new InternalException("The project " + p.getCompleteName() + " is closed, you can't edit it");
		} else if (p.isLocked() && msg.hasText()) {
			throw new InternalException("The project " + p.getCompleteName()
					+ " is locked, you can't edit it. Close all branch to unlock the project. Maybe you want edit some branch.");
		}

		File f = p.execute(msg);
		save(p);
		addUpdate(p, msg);
		return f;
	}

	public File undo(Project p, Msg msg) throws Exception {
		if (!p.isOpen()) {
			throw new InternalException("The project " + p.getCompleteName() + " is closed, you can't edit it");
		}
		if (p.isLocked()) {
			throw new InternalException("The project " + p.getCompleteName()
					+ " is locked, you can't edit it. Close all branch to unlock the project");
		}
		File f = p.undo();
		save(p);
		addUpdate(p, msg);
		return f;
	}

	public File redo(Project p, Msg msg) throws Exception {
		if (!p.isOpen()) {
			throw new InternalException("The project " + p.getCompleteName() + " is closed, you can't edit it");
		}
		if (p.isLocked()) {
			throw new InternalException("The project " + p.getCompleteName()
					+ " is locked, you can't edit it. Close all branch to unlock the project");
		}
		File f = p.redo();
		save(p);
		addUpdate(p, msg);
		return f;
	}

	/*---------------------------------------------------------------Create users and projects, remove projects------------------------------------------------------------*/

	public User addUser(User u) throws InternalException {
		User aux = null;
		if (u.getId() != -1) {
			aux = getUser(u.getId(), u.getChannel());
		}
		if (aux == null) {
			aux = getUser(u.getNick(), u.getChannel());
		}

		if (aux == null) {
			socioApp.getUsers().add(u);
			aux = u;
			register(u.getChannel());
		} else {
			if (u.getId() != -1) {
				aux.setId(u.getId());
			}
			if (!u.getNick().isEmpty()) {
				aux.setNick(u.getNick());
			}
			if (!u.getName().isEmpty()) {
				aux.setName(u.getName());
			}
		}
		// Actualizar Contribuciones.
		List<Contribution> remove = new ArrayList<>();
		for (Contribution c : aux.getContributions()) {
			if (c.getProject().isBranch() && !c.getProject().isOpen()) {
				remove.add(c);
			}
		}
		aux.getContributions().removeAll(remove);

		save(null);
		return aux;
	}

	public Project createProject(String name, User user, ProjectType type, Visibility constraint, Project father,
			String branchGroup) throws Exception {
		Project aux = getProject(name, user);
		if (aux != null) {
			throw new InternalException("The project " + name + " from the user " + user.getNick() + " already exist");
		}

		Project p;
		if (type == ProjectType.METAMODEL) {
			p = SocioProjectsFactoryImpl.eINSTANCE.createMetamodelProject();
		} else {
			p = SocioProjectsFactoryImpl.eINSTANCE.createModelProjec();
		}

		p.setName(name);
		p.setHistory(ProjectHistoryFactoryImpl.eINSTANCE.createHistory());
		p.setRemove(RemoveLogFactoryImpl.eINSTANCE.createRoot());
		p.setId(ProjectImpl.getNextId());
		p.setVisibility(constraint);
		addProject(p, user);

		if (father != null) {
			p.setBranch(true);
			father.addBranch(p, branchGroup);
			p.initialize();
			father.copyModel(p);
		} else {
			p.setBranch(false);
			p.initialize();
		}

		CreateMsg cmsg = ProjectHistoryFactoryImpl.eINSTANCE.createCreateMsg();
		cmsg.setDate(new Date());
		cmsg.setUser(user);

		p.getHistory().setCreateMsg(cmsg);
		socioApp.getProjects().add(p);
		save(p);
		return p;
	}

	public File createBranch(Project actual, User user, String branchName, String group) throws Exception {

		ProjectType pt;
		if (actual instanceof MetamodelProject) {
			pt = ProjectType.METAMODEL;
		} else {
			pt = ProjectType.MODEL;
		}
		Project p = createProject(branchName, user, pt, actual.getVisibility(), actual, group);
		save(actual);
		return p.getPng(new ArrayList<Action>());
	}

	private void addProject(Project p, User u) throws InternalException {
		Project aux = getProject(p.getName(), u);
		if (aux == null) {
			p.setAdmin(u);
			u.getOwnProjects().add(p);
			addProjectToChannel(p.getAdmin().getChannel(), p);
		} else {
			throw new InternalException(
					"The project " + p.getName() + " from the user " + u.getNick() + " already exist");
		}
	}

	public void removeProject(Project p) throws Exception {

		List<User> users = socioApp.getUsers();
		for (User u : users) {
			u.removeContribution(p);
			for (BranchGroup d : p.getBranchs()) {
				for (Project b : d.getBranchs()) {
					u.removeContribution(b);
				}
			}
		}
		p.delete();
		socioApp.getProjects().remove(p);
		save(null);
	}

	/*---------------------------------------------------------------------Configurantion projects---------------------------------------------------------------------------*/

	public Project changeProjectVisibility(Project actual, Visibility c) {
		actual.setVisibility(c);
		this.save(actual);
		return actual;

	}

	public void addUserToProject(Project actual, User u, Access access) {
		if (u.getContribution(actual) != null) {
			u.getContribution(actual).setAccess(access);
		} else {
			Contribution c = SocioProjectsFactoryImpl.eINSTANCE.createContribution();
			c.setAccess(access);
			c.setProject(actual);
			u.getContributions().add(c);
			addProjectToChannel(u.getChannel(), actual);
		}
		this.save(actual);
	}

	public void removeUserToProject(Project actual, User u) {
		Contribution c = u.getContribution(actual);
		u.getContributions().remove(c);
		this.save(actual);
	}

	public void changeBranchGroup(Project actual, String branchGroup) throws InternalException {
		actual.getFather().changeBranchGroup(actual, branchGroup);
		this.save(null);
	}

	/*---------------------------------------------------------------------------Decision maker-------------------------------------------------------------------------*/

	public void startDecision(Project actual, String branchGroup, Date date) throws InternalException {
		AdminChoice a = BranchDecisionFactoryImpl.eINSTANCE.createAdminChoice();
		a.setStart(date);
		actual.startDecision(a, branchGroup);
		this.save(actual);
	}

	public void startConsensus(Project actual, String text, List<User> users, double required, Date date) throws InternalException {

		Consensus consensus = BranchDecisionFactoryImpl.eINSTANCE.createConsensus();
		consensus.getUsers().addAll(users);
		consensus.setConsensusRequired(0.75);
		consensus.setStart(date);
		consensus.setConsensusRequired(required);
		List<Project> branchs = actual.startDecision(consensus, text);
		this.getProjects().removeAll(branchs);
		this.save(actual);
	}

	public File makeDecision(Project actual, Decision d, Project p, Date date) throws Exception {
		synchronized (d) {
			List<Action> actions = actual.makeDecision(d, p);
			d.setMergedDate(date);
			this.save(actual);
			return actual.getPng(actions);
		}
	}

	private Timer t = new Timer();

	public void startVoting(ServletContext context, Project actual, Consensus d, long timer, Date date) {
		synchronized (d) {
			Round r = BranchDecisionFactoryImpl.eINSTANCE.createRound();
			r.setRoundDate(date);
			r.setRoundId(d.getRounds().size());
			r.setTimer(timer);
			d.addRound(r);
			ConsensusTimeOut task = new ConsensusTimeOut(context, d);
			t.schedule(task, timer);
			timesOuts.put(actual.getCompleteName() + "/" + d.getName(), task);
			for (User u : d.getUsers()) {
				List<Consensus> cons = poll.get(u.getChannel());
				if (cons == null) {
					cons = new ArrayList<>();
					poll.put(u.getChannel(), cons);
				}
				if (!cons.contains(d)) {
					cons.add(d);
				}
			}
			this.save(actual);
		}
	}
	SaveFileServer server = new SaveFileServer();
	public void endVoting(Project actual, Consensus consensus) throws Exception  {
		synchronized (consensus) {
			consensus.calculateConsensus();
			File ret = null;
			if (consensus.getConsensusActualMeasure() >= consensus.getConsensusRequired()) {
				Project p = consensus.getConsensusFirstOption();
				ret = makeDecision(actual, consensus, p, new Date());
				String path = server.saveFile(ret, 100, TimeUnit.DAYS);
				consensus.setFilePath(path);
			}
			
			for (String channel : poll.keySet()) {
				if (poll.get(channel).contains(consensus)) {
					poll.get(channel).remove(consensus);
				}
			}
			List<Consensus> cons = pollEnd.get(actual.getAdmin().getChannel());
			if (cons == null) {
				cons = new ArrayList<>();
				pollEnd.put(actual.getAdmin().getChannel(), cons);
			}
			cons.add(consensus);
			for (User u: getUsers(actual)) {
				cons = pollEnd.get(u.getChannel());
				if (cons == null) {
					cons = new ArrayList<>();
					pollEnd.put(u.getChannel(), cons);
				}
				if (!cons.contains(consensus)) {
					cons.add(consensus);
				}
			}
			for (User u: consensus.getUsers()) {
				cons = pollEnd.get(u.getChannel());
				if (cons == null) {
					cons = new ArrayList<>();
					pollEnd.put(u.getChannel(), cons);
				}
				if (!cons.contains(consensus)) {
					cons.add(consensus);
				}
			}
		}
		
	}
	
	public List<Consensus> getAndRemoveEndConsensus(String channel) {
		List<Consensus> ret = null;
		if (pollEnd.get(channel) != null) {
			ret = pollEnd.get(channel);
			pollEnd.put(channel, new ArrayList<>());
		}
		return ret;
	}

	public List<Consensus> getAndRemoveConsensus(String channel) {
		List<Consensus> ret = null;
		if (poll.get(channel) != null) {
			ret = poll.get(channel);
			poll.put(channel, new ArrayList<>());
		}
		return ret;
	}

	public void addPreference(Project actual, Consensus d, User user, Map<String, Integer> pos) {
		synchronized (d) {
			PreferenceOrdering pref = BranchDecisionFactoryImpl.eINSTANCE.createPreferenceOrdering();
			pref.setUser(user);
			for (Project p : ((Consensus) d).getBranchs()) {
				Order o = BranchDecisionFactoryImpl.eINSTANCE.createOrder();
				o.setProjectName(p.getCompleteName());
				o.setPos(pos.get(p.getCompleteName()));
				pref.getOrder().add(o);
			}
			if (d.setPreference(pref) == true) {
				ConsensusTimeOut task = timesOuts.get(actual.getCompleteName() + "/" + d.getName());
				task.run();
			}
			this.save(actual);
		}
	}

	/*-----------------------------------------------------------------------------------Update status--------------------------------------------------------------------*/
	public void register(String channel) {
		channel = channel.toLowerCase();
		if (channels_projects.get(channel) == null) {
			channels_projects.put(channel, new ArrayList<>());
			updated.put(channel, new HashMap<>());
		}
	}

	public void addProjectToChannel(String channel, Project project) {
		channel = channel.toLowerCase();
		register(channel);
		List<Project> projs = channels_projects.get(channel);
		if (!projs.contains(project)) {
			projs.add(project);
			// channels_projects.put(channel, projs);
		}
	}

	public void addUpdate(Project p, Msg msg) {

		Set<String> channels = channels_projects.keySet();

		for (String channel : channels) {
			Map<Project, List<Msg>> projectsUpdates = updated.get(channel);
			if (p.getVisibility() != Visibility.PRIVATE || channels_projects.get(channel).contains(p)) {
				if (projectsUpdates.get(p) == null) {
					projectsUpdates.put(p, new ArrayList<>());
				}
				projectsUpdates.get(p).add(msg);
			}
		}

	}

	public List<Project> getUpdates(String channel) {
		channel = channel.toLowerCase();
		register(channel);
		List<Project> ret = new ArrayList<>(updated.get(channel).keySet());
		return ret;
	}

	public List<Msg> getUpdate(Project p, String channel) {
		channel = channel.toLowerCase();
		Map<Project, List<Msg>> ret = updated.get(channel);
		if (ret.containsKey(p)) {
			List<Msg> msg = ret.get(p);
			ret.remove(p);
			return msg;
		}
		return null;
	}

	public File getLastPng(Project p) {
		return p.getLastModify();
	}

}
