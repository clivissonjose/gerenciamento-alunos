package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    // NOVOS TESTES

    // Teste 1: Deletar um usuário
    @Test
    public void deletarAluno(){

        Aluno aluno = new Aluno();
        aluno.setNome("Joao Santos de Araujo Lima");
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        serviceAluno.save(aluno);
        serviceAluno.deleteById(aluno.getId());

        // Verifica se o aluno realmente foi deletado
        Assert.assertThrows(NoSuchElementException.class, () -> {

            this.serviceAluno.getById(1L) ;

        });

    }

    // Teste 2: criar um aluno sem número da matrícula

    @Test
    public void criarAlunoSemMatricula(){

        Aluno aluno = new Aluno();
        aluno.setNome("Joao Santos de Araujo Lima");
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});

    }

    // teste 3: Atualizar cadastro de aluno
    @Test
    public void atualizarAluno() {

        Aluno aluno = new Aluno();

        aluno.setNome("Carolina De Araújo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("7875655578");

        this.serviceAluno.save(aluno);

        Aluno alunoSalvo = this.serviceAluno.getById(aluno.getId());

        Assert.assertNotNull(alunoSalvo);

        alunoSalvo.setNome("Maria Aparecida");
        alunoSalvo.setCurso(Curso.DIREITO);
        this.serviceAluno.save(alunoSalvo);

        Aluno alunoAtualizado = this.serviceAluno.getById(alunoSalvo.getId());
        assertEquals("Maria Aparecida", alunoAtualizado.getNome());
        assertEquals(Curso.DIREITO, alunoAtualizado.getCurso());
    }


    // teste 4: Testar busca por nome, em qualquer formato

    @Test
    public void findByNomeContainingIgnoreCaseTest(){

        Aluno aluno = new Aluno();

        aluno.setNome("CaRoLiNa De AraÚJO");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("7875655578");

        this.serviceAluno.save(aluno);

        List<Aluno> resultado = serviceAluno.findByNomeContainingIgnoreCase("CAROLINA DE ARAÚJO");

        assertEquals(1, resultado.size());
                     //CaRoLiNa De AraÚJO
        assertEquals(aluno.getNome(), resultado.get(0).getNome());

    }

}