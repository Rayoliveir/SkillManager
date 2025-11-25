import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// --- INFO: A 'services/api.js' agora trata os erros disto ---
import { cadastrarEstagiario } from '../../services/api';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';

function CadastroEstagiario() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        dataNascimento: '',
        genero: 'MASCULINO', 
        telefone: '',
        email: '',
        cpf: '',
        senha: '',
        codigoEmpresa: '',
        endereco: {
            logradouro: '',
            bairro: '',
            cidade: '',
            numero: '',
            estados: 'SP',
            cep: ''
        },
        dadosAcademicos: {
            faculdadeCnpj: '',
            curso: '',
            periodoSemestre: '',
            previsaoFormatura: '',
            ra: ''
        }
    });
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
        if (keys.length > 1) {
            setFormData(prev => ({
                ...prev,
                [keys[0]]: {
                    ...prev[keys[0]],
                    [keys[1]]: value
                }
            }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // --- INFO: A sua lógica de payload aqui já estava correta ---
        const payload = {
            ...formData,
            cpf: formData.cpf.replace(/\D/g, ''),
            telefone: formData.telefone.replace(/\D/g, ''),
            endereco: {
                ...formData.endereco,
                cep: formData.endereco.cep.replace(/\D/g, '')
            },
            dadosAcademicos: {
                ...formData.dadosAcademicos,
                faculdadeCnpj: formData.dadosAcademicos.faculdadeCnpj.replace(/\D/g, ''),
                // --- INFO: Isto já envia 'YYYY-MM', que é o que o back-end espera. Perfeito.
                previsaoFormatura: formData.dadosAcademicos.previsaoFormatura ? formData.dadosAcademicos.previsaoFormatura.substring(0, 7) : ''
            }
        };

        try {
            await cadastrarEstagiario(payload);
            exibirMensagem('Cadastro realizado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (err) {
            // --- INFO: Esta linha já estava CORRETA.
            // Ela vai ler a mensagem do 'apiFetch' (de 'services/api.js')
            // ex: "Email já cadastrado." ou "Empresa não encontrada com o código: XYZ"
            exibirMensagem(err.message || 'Ocorreu um erro no cadastro.', 'erro');
        }
    };
    
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    const steps = [
        {
            title: "Dados Pessoais",
            content: (
                <>
                    <fieldset className="dados-pessoais">
                        <legend>Dados pessoais</legend>
                        <label>Nome completo <br />
                            <input type="text" name="nome" placeholder="Nome completo" required value={formData.nome} onChange={handleChange} />
                        </label>
                        <label>Data de nascimento <br />
                            <input type="date" name="dataNascimento" required value={formData.dataNascimento} onChange={handleChange} />
                        </label>
                        
                        <label>Gênero<br />
                            <select name="genero" value={formData.genero} onChange={handleChange} required>
                                <option value="MASCULINO">Masculino</option>
                                <option value="FEMININO">Feminino</option>
                                <option value="OUTRO">Outro</option>
                            </select>
                        </label>

                        <label>Telefone <br />
                            <input type="tel" name="telefone" placeholder="(XX) XXXXX-XXXX" required value={formData.telefone} onChange={handleChange} />
                        </label>
                        <label>CPF <br />
                            <input type="text" name="cpf" placeholder="000.000.000-00" required value={formData.cpf} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        },
        {
            title: "Endereço",
            content: (
                <>
                    <fieldset className="endereco">
                        <legend>Endereço</legend>
                        <label>Logradouro<br />
                            <input type="text" name="endereco.logradouro" placeholder="Rua / Avenida" required value={formData.endereco.logradouro} onChange={handleChange} />
                        </label>
                        <label>Número<br />
                            <input type="text" name="endereco.numero" placeholder="Número" required value={formData.endereco.numero} onChange={handleChange} />
                        </label>
                        <label>Bairro<br />
                            <input type="text" name="endereco.bairro" placeholder="Bairro" required value={formData.endereco.bairro} onChange={handleChange} />
                        </label>
                        <label>Cidade<br />
                            <input type="text" name="endereco.cidade" placeholder="Cidade" required value={formData.endereco.cidade} onChange={handleChange} />
                        </label>
                        <label>Estado<br />
                            <select name="endereco.estados" required value={formData.endereco.estados} onChange={handleChange}>
                                {estadosBrasileiros.map(uf => <option key={uf} value={uf}>{uf}</option>)}
                            </select>
                        </label>
                        <label>CEP<br />
                            <input type="text" name="endereco.cep" placeholder="00000-000" required value={formData.endereco.cep} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        },
        {
            title: "Dados de Acesso",
            content: (
                <>
                    <fieldset className="dados-acesso">
                        <legend>Dados de acesso</legend>
                        <label>E-mail<br />
                            <input type="email" name="email" placeholder="seuemail@email.com" required value={formData.email} onChange={handleChange} />
                        </label>
                        <label>Senha<br />
                            <input type="password" name="senha" placeholder="Senha" minLength="6" required value={formData.senha} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        },
        {
            title: "Dados Acadêmicos",
            content: (
                <>
                    <fieldset className="dados-academicos">
                        <legend>Dados acadêmicos</legend>
                        
                        <label>Código de Convite da Empresa <br />
                            <input type="text" name="codigoEmpresa" placeholder="ABC-123" required value={formData.codigoEmpresa} onChange={handleChange} />
                        </label>

                        <label>CNPJ da Faculdade <br />
                            <input type="text" name="dadosAcademicos.faculdadeCnpj" placeholder="00.000.000/0001-00" required value={formData.dadosAcademicos.faculdadeCnpj} onChange={handleChange} />
                        </label>
                        <label>Curso<br />
                            <input type="text" name="dadosAcademicos.curso" placeholder="Curso" required value={formData.dadosAcademicos.curso} onChange={handleChange} />
                        </label>
                        <label>Período/Semestre<br />
                            <input type="text" name="dadosAcademicos.periodoSemestre" placeholder="Ex: 4º Semestre" required value={formData.dadosAcademicos.periodoSemestre} onChange={handleChange} />
                        </label>
                        <label>Previsão de formatura<br />
                            <input type="month" name="dadosAcademicos.previsaoFormatura" required value={formData.dadosAcademicos.previsaoFormatura} onChange={handleChange} />
                        </label>
                        <label>R.A<br />
                            <input type="text" name="dadosAcademicos.ra" placeholder="Registro acadêmico" required value={formData.dadosAcademicos.ra} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        }
    ];

    return (
        <main className="main-estagiario">
            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            <div className="slider-form-wrapper">
                <h1>Cadastro de estagiário</h1>
                <SliderForm 
                    steps={steps} 
                    onSubmit={handleSubmit} 
                    formData={formData} 
                    handleChange={handleChange} 
                />
            </div>
        </main>
    );
}

export default CadastroEstagiario;