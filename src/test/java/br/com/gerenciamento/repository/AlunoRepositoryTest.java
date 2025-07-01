package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    AlunoRepository alunoRepository;


    //Teste 1: Encontrar usuário por id

  @Test
  public void getByIdTest(){

      Aluno aluno = new Aluno();
      aluno.setNome("Vinicius");
      aluno.setTurno(Turno.NOTURNO);
      aluno.setCurso(Curso.ADMINISTRACAO);
      aluno.setStatus(Status.INATIVO);
      aluno.setMatricula("rrer4r4t43");

      this.alunoRepository.save(aluno);


      Aluno alunoRetornado = this.alunoRepository.findById(aluno.getId()).get();


      assertEquals("Vinicius", alunoRetornado.getNome());
      assertTrue(alunoRetornado.getMatricula().equals(aluno.getMatricula()));
      assertTrue(alunoRetornado.getId().equals(aluno.getId()));

  }

  // Teste 2: Encontrar alunos por status ativo
  @Test
  public void findByStatusAtivoTest(){

      Aluno aluno = new Aluno();
      aluno.setNome("Vinicius Sousa");
      aluno.setTurno(Turno.NOTURNO);
      aluno.setCurso(Curso.ENFERMAGEM);
      aluno.setStatus(Status.ATIVO);
      aluno.setMatricula("123456");

      this.alunoRepository.save(aluno);

      Aluno aluno1 = new Aluno();
      aluno1.setNome("Joao Santos de Araujo Lima");
      aluno1.setTurno(Turno.MATUTINO);
      aluno1.setCurso(Curso.INFORMATICA);
      aluno1.setStatus(Status.ATIVO);
      aluno1.setMatricula("14yr8r56");

      this.alunoRepository.save(aluno1);

      List<Aluno> alunosAtivos = this.alunoRepository.findByStatusAtivo();

      assertEquals(2, alunosAtivos.size());
      assertTrue(alunosAtivos.contains(aluno));
      assertTrue(alunosAtivos.contains(aluno1));
  }

  // Teste 3: Encontrar alunos por status inativo
  @Test
  public void findByStatusInativoTest(){

      Aluno aluno = new Aluno();
      aluno.setNome("Vinicius");
      aluno.setTurno(Turno.NOTURNO);
      aluno.setCurso(Curso.DIREITO);
      aluno.setStatus(Status.INATIVO);
      aluno.setMatricula("123456");

      this.alunoRepository.save(aluno);

      Aluno aluno1 = new Aluno();
      aluno1.setNome("Joao Santos de Araujo Lima");
      aluno1.setTurno(Turno.MATUTINO);
      aluno1.setCurso(Curso.BIOMEDICINA);
      aluno1.setStatus(Status.INATIVO);
      aluno1.setMatricula("14yr8r56");

      this.alunoRepository.save(aluno1);

      List<Aluno> alunosInativos = this.alunoRepository.findByStatusInativo();

      assertEquals(2, alunosInativos.size());
      assertTrue(alunosInativos.contains(aluno));
      assertTrue(alunosInativos.contains(aluno1));

  }

  // Teste 4: Encontrar aluno como nome em Maiusculo
  @Test
  public void findByNomeContainingIgnoreCaseTest(){

      Aluno aluno = new Aluno();

      aluno.setNome("Gabriel Henrique");
      aluno.setTurno(Turno.NOTURNO);
      aluno.setCurso(Curso.CONTABILIDADE);
      aluno.setStatus(Status.ATIVO);
      aluno.setMatricula("78756pp");

      this.alunoRepository.save(aluno);

      List<Aluno> alunoRetornado = alunoRepository.findByNomeContainingIgnoreCase("GABRIEL HENRIQUE");

      assertTrue(aluno.getNome().equals(alunoRetornado.get(0).getNome()));
      assertTrue(aluno.getId().equals(alunoRetornado.get(0).getId()));
  }

}
