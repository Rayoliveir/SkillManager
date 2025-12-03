import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// --- MUDANÇA: Importa a função específica de cadastro de supervisor ---
import { cadastrarSupervisor } from '../../services/api';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';
import { User, Lock } from 'lucide-react';

function CadastroFuncionario() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        cargo: 'SUPERVISOR',
        empresaCnpj: '', // Usado se empresaCadastrada === 'sim'
        empresaCadastrada: 'sim',
        
        // Dados para nova empresa (empresaCadastrada === 'nao')
        empresa: {
            cnpj: '',
            razaoSocial: '',
            nomeFantasia: '', // No back pode estar como 'empresaNome'
            tipoEmpresa: 'SERVICO', // Valor padrão obrigatório (Enum)
            inscricaoEstadual: '', // Adicionado campo
            inscricaoMunicipal: '', // Adicionado campo
            endereco: {
                logradouro: '',
                cidade: '',
                bairro: '',
                estados: 'SP',
                numero: '',
                cep: '' // Campo obrigatório
            }
        }
    });
    
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
        if (keys.length === 3) { // ex: empresa.endereco.logradouro
            setFormData(prev => ({
                ...prev,
                empresa: {
                    ...prev.empresa,
                    endereco: {
                        ...prev.empresa.endereco,
                        [keys[2]]: value
                    }
                }
            }));
        } else if (keys.length === 2) { // ex: empresa.cnpj
            setFormData(prev => ({
                ...prev,
                empresa: {
                    ...prev.empresa,
                    [keys[1]]: value
                }
            }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        let payload;
        
        // Limpeza de caracteres não numéricos
        const cleanCNPJ = (cnpj) => cnpj ? cnpj.replace(/\D/g, '') : '';
        const cleanCEP = (cep) => cep ? cep.replace(/\D/g, '') : '';

        if (formData.empresaCadastrada === 'sim') {
            // --- FLUXO A: Empresa já existe ---
            payload = {
                nome: formData.nome,
                email: formData.email,
                senha: formData.senha,
                cargo: formData.cargo,
                empresaCnpj: cleanCNPJ(formData.empresaCnpj)
            };
        } else {
            // --- FLUXO B: Nova Empresa ---
            // Mapeando para a estrutura plana que o SupervisorDTO provavelmente espera
            payload = {
                nome: formData.nome,
                email: formData.email,
                senha: formData.senha,
                cargo: formData.cargo,
                
                // Dados da Empresa (Flattened/Planos se o DTO for assim)
                empresaCnpj: cleanCNPJ(formData.empresa.cnpj),
                empresaNome: formData.empresa.nomeFantasia,
                empresaRazaoSocial: formData.empresa.razaoSocial,
                empresaTipo: formData.empresa.tipoEmpresa,
                
                // Campos de Inscrição (Adicionados para evitar erro 400 se forem obrigatórios)
                empresaInscricaoEstadual: formData.empresa.inscricaoEstadual,
                empresaInscricaoMunicipal: formData.empresa.inscricaoMunicipal,
                
                // Objeto Endereço
                empresaEndereco: {
                    logradouro: formData.empresa.endereco.logradouro,
                    numero: formData.empresa.endereco.numero,
                    bairro: formData.empresa.endereco.bairro,
                    cidade: formData.empresa.endereco.cidade,
                    estados: formData.empresa.endereco.estados,
                    cep: cleanCEP(formData.empresa.endereco.cep)
                }
            };
        }

        try {
            await cadastrarSupervisor(payload);
            exibirMensagem('Supervisor cadastrado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { navigate('/login'); }, 2000);
        } catch (err) {
            console.error("Erro no cadastro:", err);
            exibirMensagem(err.message || 'Erro ao cadastrar. Verifique os dados.', 'erro');
        }
    };
    
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    const steps = [
        {
            title: "Dados pessoais",
            content: (
                <fieldset className="dados-pessoais">
                    <legend><User size={20} /> Dados pessoais</legend>
                    <label>Nome completo <input type="text" name="nome" placeholder="Ex: João Silva" required value={formData.nome} onChange={handleChange} /></label>
                    <label>E-mail corporativo <input type="email" name="email" placeholder="seuemail@empresa.com" required value={formData.email} onChange={handleChange} /></label>
                </fieldset>
            )
        },
        {
            title: "Empresa",
            content: (
                <fieldset className="empresa-cadastrada">
                    <legend>Empresa</legend>
                    <label>Sua empresa já está cadastrada?
                        <select name="empresaCadastrada" value={formData.empresaCadastrada} onChange={handleChange} required>
                            <option value="sim">Sim, já possui cadastro</option>
                            <option value="nao">Não, quero cadastrar agora</option>
                        </select>
                    </label>
                    
                    {formData.empresaCadastrada === 'sim' ? (
                        <label>CNPJ da empresa
                            <input type="text" name="empresaCnpj" placeholder="00.000.000/0000-00" required value={formData.empresaCnpj} onChange={handleChange} />
                        </label>
                    ) : (
                        <>
                            <h3>Dados da empresa</h3>
                            <label>CNPJ <input type="text" name="empresa.cnpj" placeholder="00.000.000/0000-00" required value={formData.empresa.cnpj} onChange={handleChange} /></label>
                            <label>Razão social <input type="text" name="empresa.razaoSocial" required value={formData.empresa.razaoSocial} onChange={handleChange} /></label>
                            <label>Nome fantasia <input type="text" name="empresa.nomeFantasia" required value={formData.empresa.nomeFantasia} onChange={handleChange} /></label>
                            
                            <label>Tipo da empresa
                                <select name="empresa.tipoEmpresa" value={formData.empresa.tipoEmpresa} onChange={handleChange} required>
                                    <option value="SERVICO">Serviço</option>
                                    <option value="COMERCIO">Comércio</option>
                                    <option value="COMERCIO_E_SERVICO">Comércio e Serviço</option>
                                </select>
                            </label>

                            {/* Campos de Inscrição (Adicionados) */}
                            <div style={{display: 'flex', gap: '10px'}}>
                                <label style={{flex: 1}}>Insc. Estadual
                                    <input type="text" name="empresa.inscricaoEstadual" placeholder="Opcional p/ Serviço" value={formData.empresa.inscricaoEstadual} onChange={handleChange} />
                                </label>
                                <label style={{flex: 1}}>Insc. Municipal
                                    <input type="text" name="empresa.inscricaoMunicipal" placeholder="Opcional p/ Comércio" value={formData.empresa.inscricaoMunicipal} onChange={handleChange} />
                                </label>
                            </div>
                            
                            <h4>Endereço da empresa</h4>
                            <div style={{display: 'flex', gap: '10px'}}>
                                <label style={{flex: 1}}>CEP <input type="text" name="empresa.endereco.cep" placeholder="00000-000" required value={formData.empresa.endereco.cep} onChange={handleChange} /></label>
                                <label style={{flex: 2}}>Logradouro <input type="text" name="empresa.endereco.logradouro" required value={formData.empresa.endereco.logradouro} onChange={handleChange} /></label>
                            </div>
                            
                            <div style={{display: 'flex', gap: '10px'}}>
                                <label style={{flex: 1}}>Número <input type="text" name="empresa.endereco.numero" required value={formData.empresa.endereco.numero} onChange={handleChange} /></label>
                                <label style={{flex: 2}}>Bairro <input type="text" name="empresa.endereco.bairro" required value={formData.empresa.endereco.bairro} onChange={handleChange} /></label>
                            </div>

                            <div style={{display: 'flex', gap: '10px'}}>
                                <label style={{flex: 2}}>Cidade <input type="text" name="empresa.endereco.cidade" required value={formData.empresa.endereco.cidade} onChange={handleChange} /></label>
                                <label style={{flex: 1}}>UF
                                    <select name="empresa.endereco.estados" required value={formData.empresa.endereco.estados} onChange={handleChange}>
                                        {estadosBrasileiros.map(uf => <option key={uf} value={uf}>{uf}</option>)}
                                    </select>
                                </label>
                            </div>
                        </>
                    )}
                </fieldset>
            )
        },
        {
            title: "Acesso",
            content: (
                <fieldset className="dados-acesso">
                    <legend><Lock size={20} /> Acesso</legend>
                    <label>Senha <input type="password" name="senha" placeholder="Min. 6 caracteres" minLength="6" required value={formData.senha} onChange={handleChange} /></label>
                    <label>Cargo
                        <select name="cargo" value={formData.cargo} onChange={handleChange} required>
                            <option value="SUPERVISOR">Supervisor</option>
                            <option value="GERENTE">Gerente</option>
                        </select>
                    </label>
                </fieldset>
            )
        }
    ];

    return (
        <main className="main-funcionario">
            <MensagemFeedback mensagem={mensagem} tipo={tipoMensagem} visivel={visivel} onClose={fecharMensagem} />
            <div className="slider-form-wrapper">
                <h1>Cadastro de Supervisor</h1>
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