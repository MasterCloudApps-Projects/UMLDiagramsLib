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

        String result = ":actor:";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithSpaces(){
        Domain domain = new Domain("domain");
        domain.addActor("actor one");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor one"));

        String result = ":actor one:";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorAndUseCase(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("use case");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorAndTwoUseCases(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("use case").addUseCase("use case2");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case2)" +
                "(use case)";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithUseCaseComposite(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("use case");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case)";
        assertThat(useCaseDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnActorWithUseCaseCompositeAndUseCase(){
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("use case").addUseCase("use case composite");

        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.addActor(domain.getActor("actor"));

        String result = ":actor:\n" +
                "(use case composite)" +
                "(use case)";
        assertThat(useCaseDiagram.print(), is(result));
    }

}