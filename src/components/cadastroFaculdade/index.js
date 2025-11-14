import './styles.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { cadastrarFaculdade } from '../../services/api';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import SliderForm from '../SliderForm';

function CadastroFaculdade() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nome: '',
        cnpj: '',
        telefone: '',
        email: '',
        site: '',
        senha: '',
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

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
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
            ...formData,
            cnpj: formData.cnpj.replace(/\D/g, ''),
            telefone: formData.telefone.replace(/\D/g, ''),
            endereco: {
                ...formData.endereco,
                cep: formData.endereco.cep.replace(/\D/g, '')
            }
        };

        try {
            await cadastrarFaculdade(payload);
            exibirMensagem('Faculdade cadastrada com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { navigate('/login'); }, 2000);
        } catch (err) {
            exibirMensagem(err.message || 'Ocorreu um erro no cadastro.', 'erro');
        }
    };
    
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    // Definindo os passos para o formulário em etapas
    const steps = [
        {
            title: "Dados Institucionais",
            content: (
                <>
                    <fieldset className="dados-institucionais">
                        <legend>Dados institucionais</legend>
                        <label>Nome da faculdade<br />
                            <input type="text" name="nome" placeholder="Ex: Unifacs" required value={formData.nome} onChange={handleChange} />
                        </label>
                        <label>CNPJ<br />
                            <input type="text" name="cnpj" placeholder="00.000.000/0000-00" required value={formData.cnpj} onChange={handleChange} />
                        </label>
                        <label>Telefone<br />
                            <input type="tel" name="telefone" placeholder="(XX) XXXX-XXXX" required value={formData.telefone} onChange={handleChange} />
                        </label>
                        <label>Site (Opcional)<br />
                            <input type="text" name="site" placeholder="www.suafaculdade.edu.br" value={formData.site} onChange={handleChange} />
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
                            <input type="text" name="endereco.logradouro" placeholder="Ex: Rua dos Bandeirantes" required value={formData.endereco.logradouro} onChange={handleChange} />
                        </label>
                        <label>Número<br />
                            <input type="text" name="endereco.numero" placeholder="Ex: 000 / 000A" required value={formData.endereco.numero} onChange={handleChange} />
                        </label>
                        <label>Bairro<br />
                            <input type="text" name="endereco.bairro" placeholder="Ex: Centro" required value={formData.endereco.bairro} onChange={handleChange} />
                        </label>
                        <label>Cidade<br />
                            <input type="text" name="endereco.cidade" placeholder="Ex: Salvador" required value={formData.endereco.cidade} onChange={handleChange} />
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
                        <label>E-mail de contato<br />
                            <input type="email" name="email" placeholder="contato@faculdade.edu.br" required value={formData.email} onChange={handleChange} />
                        </label>
                        <label>Senha<br />
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