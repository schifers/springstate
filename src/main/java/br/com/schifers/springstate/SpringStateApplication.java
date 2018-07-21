package br.com.schifers.springstate;

import br.com.schifers.springstate.enumerator.EstadoEnum;
import br.com.schifers.springstate.enumerator.EventoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import java.util.Scanner;

@SpringBootApplication
public class SpringStateApplication implements CommandLineRunner {

	@Autowired
	private StateMachine<EstadoEnum, EventoEnum> stateMachine;

	@Override
	public void run(String... args) throws Exception {
		while (!stateMachine.isComplete()) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Selecione um evento: ");
			System.out.println("(1) Cancelar (2) Deferir (3) Distribuir (4) Enviar (5) Iniciar analise");
			String tecla = keyboard.nextLine();

			stateMachine.sendEvent(EventoEnum.recuperarEventoPorCodigo(tecla));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringStateApplication.class, args);
	}
}
