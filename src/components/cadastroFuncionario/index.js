import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { cadastrarFuncionario } from '../../services/api';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';

function CadastroFuncionario() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        cargo: 'SUPERVISOR',
        empresaCnpj: '',
        // Novos campos para cadastro de empresa
        empresaCadastrada: 'sim', // 'sim' ou 'nao'
        empresa: {
            cnpj: '',
            razaoSocial: '',
            nomeFantasia: '',
            endereco: {
                logradouro: '',
                cidade: '',
                bairro: '',
                estado: 'SP',
                numero: ''
            }
        }
    });
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
        // Tratamento especial para campos da empresa
        if (name.startsWith('empresa.')) {
            if (keys.length > 2) {
                // Campos aninhados como empresa.endereco.logradouro
                setFormData(prev => ({
                    ...prev,
                    empresa: {
                        ...prev.empresa,
                        [keys[1]]: {
                            ...prev.empresa[keys[1]],
                            [keys[2]]: value
                        }
                    }
                }));
            } else {
                // Campos diretos da empresa
                setFormData(prev => ({
                    ...prev,
                    empresa: {
                        ...prev.empresa,
                        [keys[1]]: value
                    }
                }));
            }
        } else if (name === 'empresaCadastrada') {
            // Atualiza a opção de empresa cadastrada
            setFormData(prev => ({
                ...prev,
                empresaCadastrada: value
            }));
        } else {
            // Campos normais do funcionário
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            // Se a empresa não está cadastrada, precisamos cadastrá-la primeiro
            if (formData.empresaCadastrada === 'nao') {
                // Aqui você poderia chamar uma função para cadastrar a empresa
                // Por enquanto, vamos apenas mostrar uma mensagem
                console.log('Empresa a ser cadastrada:', formData.empresa);
            }
            
            // Limpa a formatação do CNPJ antes de enviar
            const payload = {
                ...formData,
                empresaCnpj: formData.empresaCnpj.replace(/\D/g, '')
            };

            await cadastrarFuncionario(payload);
            exibirMensagem('Funcionário cadastrado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { navigate('/login'); }, 2000);
        } catch (err) {
            exibirMensagem(err.message || 'Ocorreu um erro no cadastro.', 'erro');
        }
    };
    
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    // Definindo os passos para o formulário em etapas
    const steps = [
        {
            title: "Dados Pessoais",
            content: (
                <>
                    <fieldset className="dados-pessoais">
                        <legend>
                            <img src='/assets/images/icone-pessoa.png' alt='icone-pessoa' />
                            Dados pessoais
                        </legend>
                        <label>Nome completo <br />
                            <input type="text" name="nome" placeholder="Nome completo" required value={formData.nome} onChange={handleChange} />
                        </label>
                        <label>E-mail corporativo<br />
                            <input type="email" name="email" placeholder="seuemail@empresa.com" required value={formData.email} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        },
        {
            title: "Empresa",
            content: (
                <>
                    <fieldset className="empresa-cadastrada">
                        <legend>Empresa</legend>
                        <label>Sua empresa já está cadastrada?<br />
                            <select name="empresaCadastrada" value={formData.empresaCadastrada} onChange={handleChange} required>
                                <option value="sim">Minha empresa está cadastrada</option>
                                <option value="nao">Minha empresa não está cadastrada</option>
                            </select>
                        </label>
                        
                        {formData.empresaCadastrada === 'sim' ? (
                            <label>CNPJ da empresa<br />
                                <input type="text" name="empresaCnpj" placeholder="Digite o CNPJ da sua empresa" required value={formData.empresaCnpj} onChange={handleChange} />
                            </label>
                        ) : (
                            <>
                                <h3>Dados da Empresa</h3>
                                <label>CNPJ<br />
                                    <input type="text" name="empresa.cnpj" placeholder="00.000.000/0000-00" required value={formData.empresa.cnpj} onChange={handleChange} />
                                </label>
                                <label>Razão Social<br />
                                    <input type="text" name="empresa.razaoSocial" placeholder="Razão Social da Empresa" required value={formData.empresa.razaoSocial} onChange={handleChange} />
                                </label>
                                <label>Nome Fantasia<br />
                                    <input type="text" name="empresa.nomeFantasia" placeholder="Nome Fantasia da Empresa" required value={formData.empresa.nomeFantasia} onChange={handleChange} />
                                </label>
                                
                                <h4>Endereço da Empresa</h4>
                                <label>Logradouro<br />
                                    <input type="text" name="empresa.endereco.logradouro" placeholder="Rua / Avenida" required value={formData.empresa.endereco.logradouro} onChange={handleChange} />
                                </label>
                                <label>Número<br />
                                    <input type="text" name="empresa.endereco.numero" placeholder="Número" required value={formData.empresa.endereco.numero} onChange={handleChange} />
                                </label>
                                <label>Bairro<br />
                                    <input type="text" name="empresa.endereco.bairro" placeholder="Bairro" required value={formData.empresa.endereco.bairro} onChange={handleChange} />
                                </label>
                                <label>Cidade<br />
                                    <input type="text" name="empresa.endereco.cidade" placeholder="Cidade" required value={formData.empresa.endereco.cidade} onChange={handleChange} />
                                </label>
                                <label>Estado<br />
                                    <select name="empresa.endereco.estado" required value={formData.empresa.endereco.estado} onChange={handleChange}>
                                        {estadosBrasileiros.map(uf => <option key={uf} value={uf}>{uf}</option>)}
                                    </select>
                                </label>
                            </>
                        )}
                    </fieldset>
                </>
            )
        },
        {
            title: "Dados de Acesso",
            content: (
                <>
                    <fieldset className="dados-acesso">
                        <legend>
                            <img src='/assets/images/icone-cadeado.png' alt='icone-cadeado' />
                            Dados de acesso e trabalho
                        </legend>
                        <label>Senha<br />
                            <input type="password" name="senha" placeholder="Senha" minLength="6" required value={formData.senha} onChange={handleChange} />
                        </label>
                        <label>Cargo<br />
                            <select name="cargo" value={formData.cargo} onChange={handleChange} required>
                                <option value="SUPERVISOR">Supervisor</option>
                                <option value="GERENTE">Gerente</option>
                                <option value="ADMIN">Administrador</option>
                            </select>
                        </label>
                    </fieldset>
                </>
            )
        }
    ];

    return (
        <main className="main-funcionario">
            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            <div className="slider-form-wrapper">
                <h1>Cadastro de supervisor</h1>
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

export default CadastroFuncionario;