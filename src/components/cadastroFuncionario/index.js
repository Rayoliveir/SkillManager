import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// --- MUDANÇA: Importa a API correta (Supervisor) de 'services/api.js' ---
import { cadastrarSupervisor } from '../../services/api';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';
import { User, Building, Lock } from "lucide-react";

function CadastroFuncionario() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        cargo: 'SUPERVISOR',
        empresaCnpj: '', // --- INFO: Usado no Fluxo A ("Sim")
        empresaCadastrada: 'sim', 
        
        // --- INFO: Usado no Fluxo B ("Não") ---
        empresa: {
            cnpj: '', // Note: O back-end espera 'empresaCnpj' e 'empresa.cnpj'
            razaoSocial: '',
            nomeFantasia: '', // O back-end chama isto de 'nome'
            // --- CORREÇÃO: O seu back-end (EmpresaDTO) precisa disto: ---
            tipoEmpresa: 'SERVICO', // <- Adicionado valor padrão
            
            endereco: {
                logradouro: '',
                cidade: '',
                bairro: '',
                estados: 'SP', // <- Corrigido de 'estado' para 'estados'
                numero: '',
                // --- CORREÇÃO: O seu back-end (EnderecoDTO) precisa disto: ---
                cep: '' // <- Adicionado campo
            }
        }
    });
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
        if (keys.length > 1) {
            // --- INFO: Trata campos aninhados como 'empresa.cnpj', 'empresa.endereco.logradouro'
            setFormData(prev => {
                // --- INFO: Cria uma cópia profunda do objeto
                const newData = JSON.parse(JSON.stringify(prev)); 
                let current = newData;
                
                // --- INFO: Navega até o penúltimo nível
                for (let i = 0; i < keys.length - 1; i++) {
                    current = current[keys[i]];
                }
                
                // --- INFO: Define o valor no último nível
                current[keys[keys.length - 1]] = value;
                return newData;
            });
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        // --- INFO: Prepara o payload com base na escolha do usuário
        let payload;
        
        if (formData.empresaCadastrada === 'sim') {
            // --- Fluxo A: Empresa já cadastrada ---
            payload = {
                nome: formData.nome,
                email: formData.email,
                senha: formData.senha,
                cargo: formData.cargo,
                empresaCnpj: formData.empresaCnpj.replace(/\D/g, '') // Remove formatação
            };
        } else {
            // --- Fluxo B: Cadastrar nova empresa ---
            payload = {
                nome: formData.nome,
                email: formData.email,
                senha: formData.senha,
                cargo: formData.cargo,
                empresa: {
                    cnpj: formData.empresa.cnpj.replace(/\D/g, ''),
                    razaoSocial: formData.empresa.razaoSocial,
                    nome: formData.empresa.nomeFantasia, // <- Corrigido para 'nome'
                    tipoEmpresa: formData.empresa.tipoEmpresa,
                    endereco: {
                        logradouro: formData.empresa.endereco.logradouro,
                        cidade: formData.empresa.endereco.cidade,
                        bairro: formData.empresa.endereco.bairro,
                        estados: formData.empresa.endereco.estados, // <- Corrigido para 'estados'
                        numero: formData.empresa.endereco.numero,
                        cep: formData.empresa.endereco.cep.replace(/\D/g, '') // <- Adicionado
                    }
                }
            };
        }

        try {
            // --- CORREÇÃO: Chama a API correta de 'services/api.js' ---
            await cadastrarSupervisor(payload);
            exibirMensagem('Supervisor cadastrado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { navigate('/login'); }, 2000);
        } catch (err) {
            // --- MUDANÇA: Mostra o erro real da API
            // ex: "Email já cadastrado."
            // ex: "Empresa com CNPJ ... não encontrada. Para cadastrá-la..."
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
                        <legend>
                            <User size={20} />
                            Dados pessoais
                        </legend>
                        <label>Nome completo <br />
                            <input type="text" name="nome" placeholder="Ex: João Silva" required value={formData.nome} onChange={handleChange} />
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
                                <input type="text" name="empresaCnpj" placeholder="00.000.000/0000-00" required value={formData.empresaCnpj} onChange={handleChange} />
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
                                
                                {/* --- NOVO: Campo Tipo Empresa (obrigatório pelo back-end) --- */}
                                <label>Tipo da Empresa<br />
                                    <select name="empresa.tipoEmpresa" value={formData.empresa.tipoEmpresa} onChange={handleChange} required>
                                        <option value="SERVICO">Serviço</option>
                                        <option value="COMERCIO">Comércio</option>
                                        <option value="COMERCIO_E_SERVICO">Comércio e Serviço</option>
                                    </select>
                                </label>
                                
                                <h4>Endereço da Empresa</h4>
                                <label>Logradouro<br />
                                    <input type="text" name="empresa.endereco.logradouro" placeholder="Rua dos Bandeirantes" required value={formData.empresa.endereco.logradouro} onChange={handleChange} />
                                </label>
                                <label>Número<br />
                                    <input type="text" name="empresa.endereco.numero" placeholder="000 / 000A" required value={formData.empresa.endereco.numero} onChange={handleChange} />
                                </label>
                                <label>Bairro<br />
                                    <input type="text" name="empresa.endereco.bairro" placeholder="Centro" required value={formData.empresa.endereco.bairro} onChange={handleChange} />
                                </label>
                                <label>Cidade<br />
                                    <input type="text" name="empresa.endereco.cidade" placeholder="Salvador" required value={formData.empresa.endereco.cidade} onChange={handleChange} />
                                </label>
                                <label>Estado<br />
                                    {/* --- CORREÇÃO: 'name' corrigido para 'empresa.endereco.estados' --- */}
                                    <select name="empresa.endereco.estados" required value={formData.empresa.endereco.estados} onChange={handleChange}>
                                        {estadosBrasileiros.map(uf => <option key={uf} value={uf}>{uf}</option>)}
                                    </select>
                                </label>
                                {/* --- NOVO: Campo CEP (obrigatório pelo back-end) --- */}
                                <label>CEP<br />
                                    <input type="text" name="empresa.endereco.cep" placeholder="00000-000" required value={formData.empresa.endereco.cep} onChange={handleChange} />
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
                            <Lock size={20} />
                            Dados de acesso e trabalho
                        </legend>
                        <label>Senha<br />
                            <input type="password" name="senha" placeholder="*********" minLength="6" required value={formData.senha} onChange={handleChange} />
                        </label>
                        <label>Cargo<br />
                            <select name="cargo" value={formData.cargo} onChange={handleChange} required>
                                <option value="SUPERVISOR">Supervisor</option>
                                <option value="GERENTE">Gerente</option>
                                {/* --- CORREÇÃO: Opção 'ADMIN' removida (Bug do Back-end) --- */}
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