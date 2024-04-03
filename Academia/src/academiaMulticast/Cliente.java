package academiaMulticast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		
		MulticastSocketManager msm = new MulticastSocketManager();
		msm.start();

		MulticastSocket socket1 = msm.getSocket1();
		MulticastSocket socket2 = msm.getSocket2();
		InetSocketAddress grupo1 = msm.getGrupo1();
		
		System.out.println("***CANAL DO CLIENTE***\n");
		Object[] itens = {"Canal de informações sobre as aulas.", "Canal de Promoções.", "Os dois canais"};
        Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Bem-vindo ao canal de avisos da Academia das Maravilhas! Por favor, selecione uma opção: ", "Opçao", JOptionPane.INFORMATION_MESSAGE, null,itens, itens [0]); //
        
        int codigoTopico;
        
    	if("Canal de informações sobre as aulas.".equals(opcaoSelecionada)) {
    		codigoTopico = 1;
    	} else if("Canal de Promoções.".equals(opcaoSelecionada)) {
    		codigoTopico = 2;
    	} else {
    		codigoTopico = 3;
    	}
    	
    	String nome = JOptionPane.showInputDialog("Por favor, informe o seu nome: ");
		System.out.println("Bem-vindo(a) " + nome);
		
		Sender sender1 = new Sender(socket1, grupo1, codigoTopico, nome);
		
		if (codigoTopico == 1) {
			Thread thread1 = new Thread(new Receiver(socket1, nome));
			thread1.start();
			
			while(true) {
				System.out.println("Você pode enviar mensagens para o Servidor agora, se quiser sair do tópico, digite 'sair'\n");
				String msg = sc.nextLine();
				if("sair".equals(msg.toLowerCase())) {
					msg = nome + " saiu do sistema.";
					sender1.sendMessage(msg);
					break;
				} 
				sender1.sendMessage(msg);
			}

		} else if (codigoTopico == 2) {
			Thread thread2 = new Thread(new Receiver(socket2, nome));
			thread2.start();
			
			while(true) {				
				System.out.println("Se quiser sair do tópico, digite 'sair'\n");
				String msg = sc.nextLine();
				if("sair".equals(msg.toLowerCase())) {
					msg = nome + " saiu do sistema.";
					sender1.sendMessage(msg);
					break;
				} 
				
			}

		} else if (codigoTopico == 3) {
			Thread thread2 = new Thread(new Receiver(socket2, nome));
			thread2.start();
			Thread thread1 = new Thread(new Receiver(socket1, nome));
			thread1.start();
			while(true) {				
				System.out.println("Você pode enviar mensagens para o Servidor agora, se quiser sair dos tópicos, digite 'sair'\n");
				String msg = sc.nextLine();
				if("sair".equals(msg.toLowerCase())) {
					msg = nome + " saiu do sistema.";
					sender1.sendMessage(msg);
					break;
				} 
				sender1.sendMessage(msg);
			}
		}
		
		System.out.println("Saindo do(s) tópico(s) e finalizando o sistema!");
		System.exit(0);
		sc.close();
	}
}