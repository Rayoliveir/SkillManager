import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { cadastrarCoordenador } from '../../services/api'; 
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';

function CadastroFaculdade() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '', 
        faculdadeCnpj: '',
        faculdadeNome: '',
        faculdadeTelefone: '',
        faculdadeSite: '',
        endereco: {
            logradouro: '',
            bairro: '',
            cidade: '',
            numero: '',
            estados: 'SP',
            cep: ''
        }
    });
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    // --- FUNÇÕES DE MÁSCARA ---
    const maskCNPJ = (value) => {
        return value
            .replace(/\D/g, '')
            .replace(/^(\d{2})(\d)/, '$1.$2')
            .replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3')
            .replace(/\.(\d{3})(\d)/, '.$1/$2')
            .replace(/(\d{4})(\d)/, '$1-$2')
            .replace(/(-\d{2})\d+?$/, '$1'); // Limita a 14 dígitos
    };

    const maskPhone = (value) => {
        return value
            .replace(/\D/g, '')
            .replace(/(\d{2})(\d)/, '($1) $2')
            .replace(/(\d{4,5})(\d{4})/, '$1-$2')
            .replace(/(-\d{4})\d+?$/, '$1'); // Limita tamanho
    };

    const maskCEP = (value) => {
        return value
            .replace(/\D/g, '')
            .replace(/(\d{5})(\d)/, '$1-$2')
            .replace(/(-\d{3})\d+?$/, '$1');
    };

    const handleChange = (e) => {
        let { name, value } = e.target;
        const keys = name.split('.');
        
        // Aplica Máscaras
        if (name === 'faculdadeCnpj') value = maskCNPJ(value);
        if (name === 'faculdadeTelefone') value = maskPhone(value);
        if (name === 'endereco.cep') value = maskCEP(value);

        if (keys.length > 1) {
            setFormData(prev => ({
                ...prev,
                [keys[0]]: { ...prev[keys[0]], [keys[1]]: value }
            }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const payload = {
            nome: formData.nome,
            email: formData.email,
            senha: formData.senha,
            
            faculdadeCnpj: formData.faculdadeCnpj.replace(/\D/g, ''),
            faculdadeNome: formData.faculdadeNome,
            faculdadeTelefone: formData.faculdadeTelefone.replace(/\D/g, ''),
            faculdadeSite: formData.faculdadeSite,
            
            faculdadeEndereco: {
                ...formData.endereco,
                cep: formData.endereco.cep.replace(/\D/g, '')
            }
        };

        try {
            await cadastrarCoordenador(payload);
            exibirMensagem('Coordenador cadastrado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { navigate('/login'); }, 2000);
        } catch (err) {
            exibirMensagem(err.message || 'Ocorreu um erro no cadastro.', 'erro');
        }
    };
    
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    const steps = [
        {
            title: "Dados institucionais",
            content: (
                <>
                    <fieldset className="dados-institucionais">
                        <legend>Dados da faculdade</legend>
                        <label>Nome da faculdade<br />
                            <input type="text" name="faculdadeNome" placeholder="Ex: Unifacs" required value={formData.faculdadeNome} onChange={handleChange} />
                        </label>
                        <label>CNPJ<br />
                            <input type="text" name="faculdadeCnpj" placeholder="00.000.000/0000-00" required value={formData.faculdadeCnpj} onChange={handleChange} maxLength="18" />
                        </label>
                        <label>Telefone<br />
                            <input type="tel" name="faculdadeTelefone" placeholder="(XX) XXXX-XXXX" required value={formData.faculdadeTelefone} onChange={handleChange} maxLength="15" />
                        </label>
                        <label>Site (Opcional)<br />
                            <input type="text" name="faculdadeSite" placeholder="www.suafaculdade.edu.br" value={formData.faculdadeSite} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        },
        {
            title: "Endereço da faculdade",
            content: (
                <>
                    <fieldset className="endereco">
                        <legend>Endereço</legend>
                        <label>CEP<br />
                            <input type="text" name="endereco.cep" placeholder="00000-000" required value={formData.endereco.cep} onChange={handleChange} maxLength="9" />
                        </label>
                        <label>Logradouro<br />
                            <input type="text" name="endereco.logradouro" placeholder="Ex: Rua dos Bandeirantes" required value={formData.endereco.logradouro} onChange={handleChange} />
                        </label>
                        <label>Número<br />
                            <input type="text" name="endereco.numero" placeholder="Ex: 000 / 000A" required value={formData.endereco.numero} onChange={handleChange} />
                        </label>
                        <label>Bairro<br />
                            <input type="text" name="endereco.bairro" placeholder="Ex: Centro" required value={formData.endereco.bairro} onChange={handleChange} />
                        </label>
                        <div style={{display: 'flex', gap: '10px'}}>
                            <label style={{flex: 2}}>Cidade<br />
                                <input type="text" name="endereco.cidade" placeholder="Ex: Salvador" required value={formData.endereco.cidade} onChange={handleChange} />
                            </label>
                            <label style={{flex: 1}}>Estado<br />
                                <select name="endereco.estados" required value={formData.endereco.estados} onChange={handleChange}>
                                    {estadosBrasileiros.map(uf => <option key={uf} value={uf}>{uf}</option>)}
                                </select>
                            </label>
                        </div>
                    </fieldset>
                </>
            )
        },
        {
            title: "Dados de acesso do coordenador",
            content: (
                <>
                    <fieldset className="dados-acesso">
                        <legend>Dados de acesso (Seus dados)</legend>
                        <label>Seu nome completo<br />
                            <input type="text" name="nome" placeholder="Seu nome" required value={formData.nome} onChange={handleChange} />
                        </label>
                        <label>Seu e-mail de contato<br />
                            <input type="email" name="email" placeholder="contato@faculdade.edu.br" required value={formData.email} onChange={handleChange} />
                        </label>
                        <label>Sua senha<br />
                            <input type="password" name="senha" placeholder="**********" minLength="6" required value={formData.senha} onChange={handleChange} />
                        </label>
                    </fieldset>
                </>
            )
        }
    ];

    return (
        <main className="main-faculdade">
            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            <div className="slider-form-wrapper">
                <h1>Cadastro de coordenador de estágio</h1>
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

export default CadastroFaculdade;