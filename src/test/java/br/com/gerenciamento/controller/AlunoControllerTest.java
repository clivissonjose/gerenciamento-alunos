package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoController alunoController;

    @Before
    public void setup() {
        alunoRepository.deleteAll(); // limpa antes de cada teste
    }


    @Test
    public void editarNomeDoAlunoTest() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setNome("Clivisson");
        aluno.setMatricula("2025001");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        aluno = alunoRepository.save(aluno);

        mockMvc.perform(post("/editar")
                        .param("id", aluno.getId().toString())
                        .param("nome", "Antonio"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        // Aluno com nome atualizado
        assertEquals("Antonio", aluno.getNome());
    }


    @Test
    public void removerAlunoTest() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setNome("Clivisson");
        aluno.setMatricula("2025001");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        aluno = alunoRepository.save(aluno);

        mockMvc.perform(get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        assertTrue(alunoRepository.findById(aluno.getId()).isEmpty());

    }

    // Teste :       Não conseguir usar mockMvc como nos outros testes
    @Test
    public void pesquisarAlunotest() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Jade Teen");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("00000303");

        alunoRepository.save(aluno1);

        ModelAndView mv = alunoController.pesquisarAluno("Jade Teen");
        Map<String, Object> map = mv.getModel();
        Object lista = map.get("ListaDeAlunos");

        assertEquals(1, ((List<Aluno>) lista).size());
        assertEquals("00000303", ((List<Aluno>) lista).get(0).getMatricula());
    }

    @Test
    public void inserirAluno() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Jade Teen");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("00000303");
        alunoController.inserirAluno(aluno1, new MapBindingResult(new HashMap<Integer, Integer>(),"null"));

        List<Aluno> aluno = alunoRepository.findByNomeContainingIgnoreCase("Jade Teen");
        assertEquals("00000303", aluno.get(0).getMatricula());
    }

}
