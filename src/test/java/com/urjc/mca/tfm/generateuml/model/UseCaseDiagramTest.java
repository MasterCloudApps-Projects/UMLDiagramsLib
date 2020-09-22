package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class UseCaseDiagramTest {

    @Test
    void shouldBeReturnActor(){
        Domain domain = new Domain("domain");
        domain.addActor("actor");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithSpaces(){
        Domain domain = new Domain("domain");
        domain.addActor("actor one");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor one"));

        String result = ":actor one:\n";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorAndUseCase(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("use case");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)\n" +
                ":actor: --> (use case)\n";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorAndTwoUseCases(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("use case").addUseCase("use case2");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case2)\n" +
                "(use case)\n" +
                ":actor: --> (use case2)\n" +
                ":actor: --> (use case)\n";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithUseCaseComposite(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("use case");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)\n" +
                ":actor: --> (use case)\n";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithUseCaseCompositeAndUseCase(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("use case").addUseCaseToComposite("use case composite");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)\n" +
                "(use case composite)\n" +
                ":actor: --> (use case)\n" +
                "(use case) --> (use case composite)\n";

        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithUseCaseCompositeAndUseCaseCompositeAndUseCase(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("use case").addUseCaseCompositeToComposite("use case2").addUseCaseToComposite("use case composite");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)\n" +
                "(use case2)\n" +
                "(use case composite)\n" +
                ":actor: --> (use case)\n" +
                "(use case) --> (use case2)\n" +
                "(use case2) --> (use case composite)\n";

        assertThat(useCaseDiagram.print(), is(result));
    }

}