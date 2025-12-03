import React, { useState, useEffect } from 'react';
import './styles.css';
import * as api from '../../services/api';
import { X, Save } from 'lucide-react';

const ModalDadosEstagio = ({ estagiario, onClose, onUpdateSuccess }) => {
    const dadosExistentes = estagiario.dadosEstagio; 

    const [formData, setFormData] = useState({
        titulo: '',
        dataInicio: '',
        dataTermino: '',
        cargaHoraria: '',
        tipoEstagio: 'OBRIGATORIO',
        tipoRemuneracao: 'REMUNERADO',
        observacoes: '' // --- NOVO ESTADO ---
    });
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (dadosExistentes) {
            setFormData({
                titulo: dadosExistentes.titulo || '',
                dataInicio: dadosExistentes.dataInicio || '',
                dataTermino: dadosExistentes.dataTermino || '',
                cargaHoraria: dadosExistentes.cargaHoraria || '',
                tipoEstagio: dadosExistentes.tipoEstagio || 'OBRIGATORIO',
                tipoRemuneracao: dadosExistentes.tipoRemuneracao || 'REMUNERADO',
                observacoes: dadosExistentes.observacoes || '' // --- CARREGA OBS ---
            });
        }
    }, [dadosExistentes]);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const payload = {
                titulo: formData.titulo,
                dataInicio: formData.dataInicio,
                dataTermino: formData.dataTermino === '' ? null : formData.dataTermino,
                cargaHoraria: parseInt(formData.cargaHoraria, 10), 
                tipoEstagio: formData.tipoEstagio,
                tipoRemuneracao: formData.tipoRemuneracao,
                observacoes: formData.observacoes, // --- ENVIA OBS ---
                estagiarioId: estagiario.id
            };

            if (dadosExistentes && dadosExistentes.id) {
                await api.atualizarDadosEstagio(dadosExistentes.id, payload);
                alert('Dados atualizados com sucesso!');
            } else {
                await api.salvarDadosEstagio(payload);
                alert('Dados cadastrados com sucesso!');
            }
            
            onUpdateSuccess();
            onClose();
        } catch (error) {
            alert('Erro ao salvar: ' + (error.message || 'Verifique os dados.'));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="overlay-dados-estagio">
            <div className="modal-dados-estagio">
                <div className="cabecalho-modal">
                    <div>
                        <h3>Dados do estágio</h3>
                        <p>Estagiário: <strong>{estagiario.nome}</strong></p>
                    </div>
                    <button className="btn-fechar-modal" onClick={onClose}>
                        <X size={24} />
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="corpo-modal">
                    <div className="form-group">
                        <label>Título do estágio / Cargo</label>
                        <input type="text" name="titulo" value={formData.titulo} onChange={handleChange} className="input-modal" placeholder="Ex: Desenvolvedor Front-end" required />
                    </div>

                    <div className="grid-modal">
                        <div className="grupo-input-modal">
                            <label>Data de início</label>
                            <input type="date" name="dataInicio" value={formData.dataInicio} onChange={handleChange} className="input-modal" required />
                        </div>
                        <div className="grupo-input-modal">
                            <label>Data de término (Opcional)</label>
                            <input type="date" name="dataTermino" value={formData.dataTermino} onChange={handleChange} className="input-modal" />
                        </div>
                    </div>

                    <div className="grid-modal">
                        <div className="grupo-input-modal">
                            <label>Carga horária (h/semana)</label>
                            <input type="number" name="cargaHoraria" value={formData.cargaHoraria} onChange={handleChange} className="input-modal" placeholder="Ex: 30" required />
                        </div>
                        <div className="grupo-input-modal">
                            <label>Tipo de estágio</label>
                            <select name="tipoEstagio" value={formData.tipoEstagio} onChange={handleChange} className="select-modal">
                                <option value="OBRIGATORIO">Obrigatório</option>
                                <option value="NAO_OBRIGATORIO">Não Obrigatório</option>
                            </select>
                        </div>
                    </div>

                    <div className="form-group">
                        <label>Remuneração</label>
                        <select name="tipoRemuneracao" value={formData.tipoRemuneracao} onChange={handleChange} className="select-modal">
                            <option value="REMUNERADO">Remunerado (Bolsa auxílio)</option>
                            <option value="VOLUNTARIO">Voluntário (Sem bolsa)</option>
                        </select>
                    </div>

                    {/* --- CAMPO DE OBSERVAÇÕES --- */}
                    <div className="form-group">
                        <label>Observações (Opcional)</label>
                        <textarea 
                            name="observacoes" 
                            value={formData.observacoes} 
                            onChange={handleChange} 
                            className="input-modal" 
                            rows="3"
                            placeholder="Informações adicionais sobre o contrato..."
                            style={{resize: 'vertical'}} 
                        />
                    </div>

                    <div className="modal-actions">
                        <button type="button" className="btn-secondary-modal" onClick={onClose}>Cancelar</button>
                        <button type="submit" className="btn-primary-modal" disabled={loading}>
                            <Save size={16} style={{marginRight:'5px'}} />
                            {loading ? '...' : 'Salvar dados'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ModalDadosEstagio;