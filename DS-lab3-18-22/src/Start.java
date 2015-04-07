import java.rmi.RemoteException;
import java.util.ArrayList;

public class Start {

	public static void main(String[] args) {

		final Configuration config = new Configuration("/tmp/DS-3/DS-lab3-18-22/config.txt");

		// run the servers
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					new Server((short) config.getServerPort(),
							config.getNumOfAccesses(),
							config.getNumberOfReaders(),
							config.getNumberOfWriters(),
							config.getServerAddrs());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
		String path = "/lab3";

		ArrayList<Client_Info> Clients_set = config.getClients();

		String[] port = new String[2];

		try {
			for (int i = 0; i < Clients_set.size(); i++) {

				Client_Info client = Clients_set.get(i);

				port = client.getAddress().split(",");

				if (port[1].equals("0")) {

					port[1] = "";

				} else {

					port[1] = "-p " + port[1];
				}

				Runtime.getRuntime()
						.exec("ssh "
								+ port[1]
								+ " "
								+ port[0]
								+ " cd \""
								+ path
								+ "/bin\" ;java "
								+ "-Djava.security.manager -Djava.security.policy=rmi.policy -Djava.rmi.server.hostname="
								+ client.getAddress().split("@")[1]
								+ " Client " + client.getId() + " "
								+ config.getServerAddrs() + " "
								+ config.getServerPort() + " "
								+ client.getType() + " "
								+ config.getNumOfAccesses());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
