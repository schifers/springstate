package br.com.schifers.springstate.config;

import br.com.schifers.springstate.enumerator.EstadoEnum;
import br.com.schifers.springstate.enumerator.EventoEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<EstadoEnum, EventoEnum> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<EstadoEnum, EventoEnum> config) throws Exception {
        config.withConfiguration().autoStartup(true).listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<EstadoEnum, EventoEnum> states) throws Exception {
        states.withStates().initial(EstadoEnum.CADASTRADA).states(EnumSet.allOf(EstadoEnum.class)).end(EstadoEnum.DEFERIDA).end(EstadoEnum.CANCELADA);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EstadoEnum, EventoEnum> transitions) throws Exception {
        transitions
                .withExternal().source(EstadoEnum.CADASTRADA).target(EstadoEnum.AGUARDANDO_DISTRIBUICAO).event(EventoEnum.ENVIAR).and()
                .withExternal().source(EstadoEnum.CADASTRADA).target(EstadoEnum.CANCELADA).event(EventoEnum.CANCELAR).and()
                .withExternal().source(EstadoEnum.AGUARDANDO_DISTRIBUICAO).target(EstadoEnum.AGUARDANDO_ANALISE).event(EventoEnum.DISTRIBUIR).and()
                .withExternal().source(EstadoEnum.AGUARDANDO_DISTRIBUICAO).target(EstadoEnum.CANCELADA).event(EventoEnum.CANCELAR).and()
                .withExternal().source(EstadoEnum.AGUARDANDO_ANALISE).target(EstadoEnum.EM_ANALISE).event(EventoEnum.INICIAR_ANALISE).and()
                .withExternal().source(EstadoEnum.AGUARDANDO_ANALISE).target(EstadoEnum.CANCELADA).event(EventoEnum.CANCELAR).and()
                .withExternal().source(EstadoEnum.EM_ANALISE).target(EstadoEnum.DEFERIDA).event(EventoEnum.DEFERIR).and()
                .withExternal().source(EstadoEnum.EM_ANALISE).target(EstadoEnum.CANCELADA).event(EventoEnum.CANCELAR);
    }

    @Bean
    public StateMachineListener<EstadoEnum, EventoEnum> listener() {
        return new StateMachineListenerAdapter<EstadoEnum, EventoEnum>() {
            @Override
            public void stateChanged(State<EstadoEnum, EventoEnum> from, State<EstadoEnum, EventoEnum> to) {
                System.out.println("Solicitacao - " + to.getId());
            }
        };
    }
}
