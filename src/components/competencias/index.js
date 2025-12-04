import React, { useState, useEffect, useCallback } from 'react';
import './styles.css';
import * as api from '../../services/api';
import { Trash2, X } from 'lucide-react';

const ModalCompetencias = ({ estagiario, onClose }) => {
    const [competencias, setCompetencias] = useState([]);
    const [loading, setLoading] = useState(false);
    const [novaCompetencia, setNovaCompetencia] = useState('');
    const [novoNivel, setNovoNivel] = useState('INICIANTE');
    const [adicionando, setAdicionando] = useState(false);

    const niveisLegiveis = {
        'INICIANTE': 'Iniciante',
        'BASICO': 'Básico',
        'INTERMEDIARIO': 'Intermediário',
        'AVANCADO': 'Avançado'
    };

    // CORREÇÃO: Função envolvida em useCallback
    const carregarCompetencias = useCallback(async () => {
        if (!estagiario) return;
        try {
            setLoading(true);
            const dados = await api.getCompetencias(estagiario.id);
            setCompetencias(dados);
        } catch (error) {
            console.error("Erro ao carregar competências:", error);
        } finally {
            setLoading(false);
        }
    }, [estagiario]); // Dependência correta

    // CORREÇÃO: useEffect dependendo da função estável
    useEffect(() => {
        carregarCompetencias();
    }, [carregarCompetencias]);

    const handleAdicionar = async (e) => {
        e.preventDefault();
        if (!novaCompetencia.trim()) return;

        try {
            setAdicionando(true);
            await api.adicionarCompetencia({
                nome: novaCompetencia,
                nivel: novoNivel, 
                estagiarioId: estagiario.id
            });
            
            setNovaCompetencia('');
            setNovoNivel('INICIANTE');
            await carregarCompetencias();
            
        } catch (error) {
            alert("Erro ao adicionar: " + (error.message || "Tente novamente."));
        } finally {
            setAdicionando(false);
        }
    };

    const handleRemover = async (id) => {
        if (!window.confirm("Remover esta competência?")) return;
        
        try {
            await api.removerCompetencia(id);
            setCompetencias(prev => prev.filter(c => c.id !== id));
        } catch (error) {
            alert("Erro ao remover: " + error.message);
        }
    };

    return (
        <div className="overlay-competencias">
            <div className="modal-competencias">
                <div className="cabecalho-modal">
                    <div>
                        <h3>Gerenciar competências</h3>
                        <p>Estagiário: <strong>{estagiario.nome}</strong></p>
                    </div>
                    <button className="btn-fechar-modal" onClick={onClose}>
                        <X size={24} />
                    </button>
                </div>

                <div className="corpo-modal">
                    <form onSubmit={handleAdicionar} className="form-adicionar">
                        <div className="grupo-input-modal" style={{flex: 2}}>
                            <label>Competência (Ex: Java, Excel)</label>
                            <input 
                                type="text" 
                                value={novaCompetencia}
                                onChange={(e) => setNovaCompetencia(e.target.value)}
                                placeholder="Digite a habilidade..."
                                required
                                className="input-modal"
                            />
                        </div>
                        <div className="grupo-input-modal" style={{flex: 1}}>
                            <label>Nível</label>
                            <select 
                                value={novoNivel} 
                                onChange={(e) => setNovoNivel(e.target.value)}
                                className="select-modal"
                            >
                                <option value="INICIANTE">Iniciante</option>
                                <option value="BASICO">Básico</option>
                                <option value="INTERMEDIARIO">Intermediário</option>
                                <option value="AVANCADO">Avançado</option>
                            </select>
                        </div>
                        <button type="submit" className="btn-adicionar" disabled={adicionando}>
                            {adicionando ? '...' : '+'}
                        </button>
                    </form>

                    <div className="divisor-modal"></div>

                    <div className="lista-competencias">
                        <h4>Competências atuais</h4>
                        
                        {loading ? (
                            <p className="loading-text">Carregando...</p>
                        ) : competencias.length > 0 ? (
                            <ul className="itens-competencia">
                                {competencias.map(comp => (
                                    <li key={comp.id} className="item-competencia">
                                        <div className="info-comp">
                                            <span className="nome-comp">{comp.nome}</span>
                                            <span className={`badge-nivel ${comp.nivel.toLowerCase()}`}>
                                                {niveisLegiveis[comp.nivel] || comp.nivel}
                                            </span>
                                        </div>
                                        <button 
                                            className="btn-remover-comp" 
                                            onClick={() => handleRemover(comp.id)}
                                            title="Remover"
                                        >
                                            <Trash2 size={18} />
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <div className="sem-dados-modal">
                                <p>Nenhuma competência cadastrada.</p>
                                <small>Use o formulário acima para adicionar.</small>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ModalCompetencias;