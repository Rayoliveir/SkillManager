import './styles.css'
// import Header from '../header';

function CadastroEstagiario() {
    return (
        <main class="main-estagiario">
            <form action="" method="post" class="cadastro-estagiario">
                <h1>Cadastro de Estagiário</h1>

                {/* <!-- Dados Pessoais --> */}
                <fieldset class="dados-pessoais">
                    <legend>
                        <img src='/assets/images/icone-pessoa.png' alt='icone-pessoa' />
                        Dados Pessoais
                    </legend>

                    <label for="nome">Nome Completo <br />
                        <input type="text" id="nome" name="nome" placeholder="Nome completo" required />
                    </label>


                    <label for="dataNascimento">Data de Nascimento <br />
                        <input type="date" id="dataNascimento" name="dataNascimento" required />
                    </label>

                    <label for="telefone">Telefone <br />
                        <input type="tel" id="telefone" name="telefone" placeholder="(XX) XXXXX-XXXX" pattern="\(\d{2}\)\s\d{4,5}-\d{4}" required /></label>

                    <label for="rg">RG <br />
                        <input type="text" id="rg" name="rg" placeholder="RG" maxlength="12" /></label>

                    <label for="cpf">CPF <br />
                        <input type="text" id="cpf" name="cpf" placeholder="000.000.000-00" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" maxlength="14" required /></label>
                </fieldset>

                {/* <!-- Endereço --> */}
                <fieldset class="endereco">
                    <legend>
                        <img src='/assets/images/icone-localizacao.png' alt='icone-localizacao' />
                        Endereço
                    </legend>

                    <label for="logradouro">Logradouro<br />
                        <input type="text" id="logradouro" name="logradouro" placeholder="Rua / Avenida" required /></label>

                    <label for="numero">Número<br />
                        <input type="text" id="numero" name="numero" placeholder="Número" required /></label>

                    <label for="bairro">Bairro<br />
                        <input type="text" id="bairro" name="bairro" placeholder="Bairro" required /></label>

                    <label for="cidade">Cidade<br />
                        <input type="text" id="cidade" name="cidade" placeholder="Cidade" required /></label>

                    <label for="estado">Estado<br />
                        <input type="text" id="estado" name="estado" placeholder="Estado" maxlength="2" required /></label>

                    <label for="cep">CEP<br />
                        <input type="text" id="cep" name="cep" placeholder="00000-000" pattern="\d{5}-\d{3}" maxlength="9" required /></label>
                </fieldset>

                {/* <!-- Dados de Acesso --> */}
                <fieldset class="dados-acesso">
                    <legend>
                        <img src='/assets/images/icone-cadeado.png' alt='icone-cadeado' />
                        Dados de Acesso
                    </legend>

                    <label for="email">E-mail<br />
                        <input type="email" id="email" name="email" placeholder="seuemail@email.com" required /></label>

                    <label for="senha">Senha<br />
                        <input type="password" id="senha" name="senha" placeholder="Senha" minlength="6" required /></label>
                </fieldset>

                {/* <!-- Dados do Estágio --> */}
                <fieldset class="dados-estagio">
                    <legend>
                        <img src='/assets/images/icone-estagio.png' alt='icone-estagio' />
                        Dados do Estágio
                    </legend>

                    <label for="titulo-de-estagio">Título do Estágio<br />
                        <input type="text" id="titulo-de-estagio" name="titulo-de-estagio" placeholder="Título do estágio" required /></label>

                    <label for="tipo-de-estagio">Tipo de Estágio <br />
                        <select id="tipo-de-estagio" name="tipo-de-estagio" required >
                            <option value="" disabled selected>Selecione</option>
                            <option value="obrigatorio">Obrigatório</option>
                            <option value="nao-obrigatorio">Não Obrigatório</option>
                        </select> </label>

                    <label for="carga-horaria">Carga Horária<br />
                        <input type="number" id="carga-horaria" name="carga-horaria" placeholder="Horas semanais" min="1" required /></label>

                    <label for="status-remuneracao">Status de Remuneração<br />
                        <input type="text" id="status-remuneracao" name="status-remuneracao" placeholder="Ex: Remunerado / Não remunerado" /></label>

                    <label for="data-inicio">Data de Início<br />
                        <input type="date" id="data-inicio" name="data-inicio" required /></label>

                    <label for="data-termino">Data de Término<br />
                        <input type="date" id="data-termino" name="data-termino" required /></label>

                    <label for="funcionario-supervisor">Funcionário Supervisor<br />
                        <input type="text" id="funcionario-supervisor" name="funcionario-supervisor" placeholder="Nome do supervisor" /></label>

                    <label for="observacoes">Observações <br />
                    <textarea id="observacoes" name="observacoes" placeholder="Digite observações adicionais" maxLength={500} cols={40} rows={10}></textarea></label>

                </fieldset>

                {/* <!-- Dados Acadêmicos --> */}
                <fieldset class="dados-academicos">
                    <legend>
                        <img src='/assets/images/icone-academico.png' alt='icone academico' />
                        Dados Acadêmicos
                    </legend>

                    <label for="nome-faculdade">Nome da Faculdade <br />
                        <input type="text" id="nome-faculdade" name="nome-faculdade" placeholder="Faculdade" required /> </label>

                    <label for="curso">Curso<br />
                        <input type="text" id="curso" name="curso" placeholder="Curso" required /> </label>

                    <label for="periodo">Período<br />
                        <input type="text" id="periodo" name="periodo" placeholder="Período" required /> </label>

                    <label for="previsao-formatura">Previsão de Formatura<br />
                        <input type="date" id="previsao-formatura" name="previsao-formatura" required /> </label>

                    <label for="ra">R.A<br />
                        <input type="text" id="ra" name="ra" placeholder="Registro acadêmico" required /></label>
                </fieldset>

                {/* <!-- Botão --> */}
                <br></br>
                <button type="submit">Cadastrar estagiário</button>
                <br></br>
            </form>
        </main>
    );
}

export default CadastroEstagiario;