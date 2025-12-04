// Função utilitária para formatar datas no relatório
const formatarData = (dataString) => {
    if (!dataString) return 'Não informada';
    try {
        const date = new Date(dataString);
        // Ajusta o fuso horário para exibir a data correta
        const userTimezoneOffset = date.getTimezoneOffset() * 60000;
        const adjustedDate = new Date(date.getTime() + userTimezoneOffset);
        return adjustedDate.toLocaleDateString('pt-BR');
    } catch (e) {
        return dataString;
    }
};

// Mapas de Enums
const niveisCompetencia = { 'INICIANTE': 'Iniciante', 'BASICO': 'Básico', 'INTERMEDIARIO': 'Intermediário', 'AVANCADO': 'Avançado', 'ESPECIALISTA': 'Especialista' };
const tipoRemuneracaoMap = { 'REMUNERADO': 'Remunerado (Bolsa)', 'VOLUNTARIO': 'Voluntário' };
const tipoEstagioMap = { 'OBRIGATORIO': 'Obrigatório', 'NAO_OBRIGATORIO': 'Não Obrigatório' };

// --- 1. RELATÓRIO INDIVIDUAL (EXISTENTE) ---
export const gerarHtmlRelatorio = (relatorio, dadosEstagio, listaCompetencias) => {
    const nomeAluno = relatorio.estagiario?.nome || 'Estagiário';
    const nomeSupervisor = relatorio.supervisor?.nome || 'Supervisor';
    const nomeEmpresa = relatorio.supervisor?.empresa?.nome || 'Empresa';
    const dataAvaliacao = formatarData(relatorio.dataAvaliacao);

    const htmlCompetencias = listaCompetencias && listaCompetencias.length > 0
        ? listaCompetencias.map(c => `
            <li class="comp-item">
                <span class="comp-nome">${c.nome}</span>
                <span class="comp-nivel">${niveisCompetencia[c.nivel] || c.nivel}</span>
            </li>`).join('')
        : '<li style="color: #718096; font-style: italic;">Nenhuma competência registrada.</li>';

    let htmlDadosContrato = '<p style="color: #718096; font-style: italic;">Dados do contrato não cadastrados.</p>';
    if (dadosEstagio) {
        htmlDadosContrato = `
            <div class="contrato-grid">
                <div class="contrato-item"><strong>Cargo:</strong> ${dadosEstagio.titulo || '-'}</div>
                <div class="contrato-item"><strong>Carga horária:</strong> ${dadosEstagio.cargaHoraria ? dadosEstagio.cargaHoraria + 'h/semana' : '-'}</div>
                <div class="contrato-item"><strong>Início:</strong> ${formatarData(dadosEstagio.dataInicio)}</div>
                <div class="contrato-item"><strong>Término:</strong> ${formatarData(dadosEstagio.dataTermino)}</div>
                <div class="contrato-item"><strong>Tipo:</strong> ${tipoEstagioMap[dadosEstagio.tipoEstagio] || dadosEstagio.tipoEstagio || '-'}</div>
                <div class="contrato-item"><strong>Remuneração:</strong> ${tipoRemuneracaoMap[dadosEstagio.tipoRemuneracao] || dadosEstagio.tipoRemuneracao || '-'}</div>
            </div>
            ${dadosEstagio.observacoes ? `<div style="margin-top: 10px;"><strong>Observações do contrato:</strong><p style="margin: 5px 0; white-space: pre-wrap;">${dadosEstagio.observacoes}</p></div>` : ''}
        `;
    }

    return `
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Relatório de desempenho - ${nomeAluno}</title>
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
            body { font-family: 'Inter', sans-serif; line-height: 1.6; color: #2D3748; margin: 0; padding: 40px; background: #F7FAFC; }
            .container { max-width: 850px; margin: 0 auto; background: #FFFFFF; padding: 40px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); border: 1px solid #E2E8F0; }
            .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 2px solid #F47C2A; padding-bottom: 20px; }
            .header h1 { font-size: 28px; font-weight: 700; color: #1A202C; margin: 0; }
            .brand { color: #F47C2A; font-weight: 700; font-size: 20px; }
            .section-title { font-size: 18px; font-weight: 600; color: #2C5282; margin-bottom: 15px; display: flex; align-items: center; }
            .section-title::before { content: ''; display: inline-block; width: 4px; height: 18px; background: #F47C2A; margin-right: 10px; border-radius: 2px; }
            .info-box { background: #EDF2F7; padding: 20px; border-radius: 8px; margin-bottom: 30px; display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
            .info-item { display: flex; flex-direction: column; }
            .info-label { font-size: 12px; text-transform: uppercase; color: #718096; font-weight: 600; }
            .info-value { font-size: 16px; font-weight: 500; color: #1A202C; }
            .contrato-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }
            .contrato-item { font-size: 14px; }
            .two-col-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; margin-bottom: 30px; }
            .card-box { background: #fff; border: 1px solid #E2E8F0; border-radius: 8px; padding: 20px; height: 100%; }
            .notas-list { list-style: none; padding: 0; margin: 0; }
            .nota-item { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; padding-bottom: 8px; border-bottom: 1px dashed #E2E8F0; }
            .nota-label { font-weight: 500; }
            .nota-badge { background: #FEEBC8; color: #C05621; font-weight: 700; padding: 4px 12px; border-radius: 20px; font-size: 14px; }
            .comp-list { list-style: none; padding: 0; margin: 0; }
            .comp-item { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; background: #F7FAFC; padding: 8px 12px; border-radius: 6px; }
            .comp-nome { font-weight: 500; }
            .comp-nivel { font-size: 12px; background: #EBF4FF; color: #2B6CB0; padding: 2px 8px; border-radius: 4px; font-weight: 600; text-transform: uppercase; }
            .feedback-text { white-space: pre-wrap; background: #F0FFF4; padding: 20px; border-radius: 8px; border-left: 4px solid #48BB78; font-style: italic; color: #2F855A; }
            .footer { text-align: center; margin-top: 50px; font-size: 12px; color: #A0AEC0; border-top: 1px solid #E2E8F0; padding-top: 20px; }
            @media print {
                body { padding: 0; background: white; }
                .container { box-shadow: none; border: none; max-width: 100%; padding: 20px; }
                .no-print { display: none !important; }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header"><h1>Relatório de desempenho</h1><span class="brand">SkillManager</span></div>
            <div class="info-box">
                <div class="info-item"><span class="info-label">Estagiário</span><span class="info-value">${nomeAluno}</span></div>
                <div class="info-item"><span class="info-label">Data da avaliação</span><span class="info-value">${dataAvaliacao}</span></div>
                <div class="info-item"><span class="info-label">Supervisor</span><span class="info-value">${nomeSupervisor}</span></div>
                <div class="info-item"><span class="info-label">Empresa</span><span class="info-value">${nomeEmpresa}</span></div>
            </div>
            <h2 class="section-title">Dados do contrato de estágio</h2>
            <div class="card-box" style="margin-bottom: 30px; background: #FEF8EC; border-color: #FBD38D;">${htmlDadosContrato}</div>
            <div class="two-col-grid">
                <div><h2 class="section-title">Avaliação de desempenho</h2><div class="card-box"><ul class="notas-list"><li class="nota-item"><span class="nota-label">Desempenho geral</span><span class="nota-badge">${relatorio.notaDesempenho}/5</span></li><li class="nota-item"><span class="nota-label">Habilidades técnicas</span><span class="nota-badge">${relatorio.notaHabilidadesTecnicas}/5</span></li><li class="nota-item"><span class="nota-label">Habilidades comportamentais</span><span class="nota-badge">${relatorio.notaHabilidadesComportamentais}/5</span></li></ul></div></div>
                <div><h2 class="section-title">Competências registradas</h2><div class="card-box"><ul class="comp-list">${htmlCompetencias}</ul></div></div>
            </div>
            <h2 class="section-title">Feedback do supervisor</h2>
            <div class="feedback-text">${relatorio.feedback}</div>
            <div class="footer"><p>Documento gerado eletronicamente pelo sistema SkillManager em ${new Date().toLocaleDateString()}.</p></div>
            <div style="text-align: center; margin-top: 30px;" class="no-print"><button onclick="window.print()" style="background: #F47C2A; color: white; border: none; padding: 12px 24px; border-radius: 6px; font-weight: 600; cursor: pointer; font-size: 16px;"> Imprimir relatório</button></div>
        </div>
    </body>
    </html>
    `;
};

// --- 2. RELATÓRIO HISTÓRICO (NOVO) ---
export const gerarHtmlRelatorioHistorico = (estagiario, listaAvaliacoes, listaCompetencias, dadosEstagio) => {
    const nomeAluno = estagiario.nome || 'Estagiário';
    const nomeEmpresa = estagiario.empresa?.nome || 'Empresa';
    
    // Competências
    const htmlCompetencias = listaCompetencias && listaCompetencias.length > 0
    ? listaCompetencias.map(c => `<span style="background:#EBF4FF; color:#2B6CB0; padding:4px 8px; border-radius:4px; font-size:12px; font-weight:600; margin-right:5px; display:inline-block; margin-bottom:5px;">${c.nome} (${niveisCompetencia[c.nivel] || c.nivel})</span>`).join('')
    : '<span style="color:#718096;">Nenhuma competência.</span>';

    // Loop de Avaliações
    const htmlAvaliacoes = listaAvaliacoes && listaAvaliacoes.length > 0
        ? listaAvaliacoes.map((avaliacao, index) => {
            const numero = listaAvaliacoes.length - index;
            return `
                <div class="avaliacao-card">
                    <div class="avaliacao-header">
                        <span class="badge-numero">#${numero}</span>
                        <span><strong>Data:</strong> ${formatarData(avaliacao.dataAvaliacao)}</span>
                        <span><strong>Supervisor:</strong> ${avaliacao.supervisor?.nome || 'N/A'}</span>
                    </div>
                    <div class="avaliacao-body">
                        <div class="notas-resumo">
                            <div class="nota-box"><span>Geral</span><strong>${avaliacao.notaDesempenho}</strong></div>
                            <div class="nota-box"><span>Técnica</span><strong>${avaliacao.notaHabilidadesTecnicas}</strong></div>
                            <div class="nota-box"><span>Comport.</span><strong>${avaliacao.notaHabilidadesComportamentais}</strong></div>
                        </div>
                        <div class="feedback-historico">
                            <strong>Feedback:</strong><br/>
                            ${avaliacao.feedback}
                        </div>
                    </div>
                </div>
            `;
        }).join('')
        : '<p style="text-align:center; padding:20px; color:#718096;">Nenhum histórico de avaliações encontrado.</p>';

    return `
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Histórico - ${nomeAluno}</title>
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
            body { font-family: 'Inter', sans-serif; color: #2D3748; margin: 0; padding: 40px; background: #F7FAFC; }
            .container { max-width: 900px; margin: 0 auto; background: #FFFFFF; padding: 40px; border-radius: 12px; border: 1px solid #E2E8F0; }
            .header { border-bottom: 2px solid #F47C2A; padding-bottom: 20px; margin-bottom: 30px; display:flex; justify-content:space-between; align-items:center; }
            .header h1 { margin: 0; color: #2C5282; font-size: 24px; }
            .info-section { background: #EDF2F7; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
            .info-section h3 { margin-top:0; color:#2C5282; border-bottom:1px solid #CBD5E0; padding-bottom:5px; }
            .comp-container { margin-top: 10px; }
            .avaliacao-card { border: 1px solid #E2E8F0; border-radius: 8px; margin-bottom: 20px; background: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.05); overflow: hidden; }
            .avaliacao-header { background: #F7FAFC; padding: 10px 20px; border-bottom: 1px solid #E2E8F0; display: flex; justify-content: space-between; align-items: center; font-size: 14px; color: #4A5568; }
            .badge-numero { background: #F47C2A; color: white; padding: 2px 8px; border-radius: 4px; font-weight: bold; font-size: 12px; }
            .avaliacao-body { padding: 20px; }
            .notas-resumo { display: flex; gap: 20px; margin-bottom: 15px; }
            .nota-box { display: flex; flex-direction: column; align-items: center; background: #FEF8EC; padding: 8px 15px; border-radius: 6px; border: 1px solid #FBD38D; min-width: 80px; }
            .nota-box span { font-size: 11px; text-transform: uppercase; color: #C05621; font-weight: 700; }
            .nota-box strong { font-size: 18px; color: #9C4221; }
            .feedback-historico { background: #F0FFF4; padding: 15px; border-radius: 6px; border-left: 3px solid #48BB78; font-size: 14px; color: #2F855A; line-height: 1.5; white-space: pre-wrap; }
            .footer { text-align:center; margin-top:50px; font-size:12px; color:#A0AEC0; border-top:1px solid #E2E8F0; padding-top:20px; }
            @media print {
                body { padding: 0; background: white; }
                .container { border: none; box-shadow: none; max-width: 100%; }
                .no-print { display: none !important; }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div><h1>Histórico de progresso</h1><div style="font-size: 14px; color: #718096; margin-top: 5px;">Relatório consolidado de feedbacks</div></div>
                <div style="font-weight:700; font-size:20px; color:#F47C2A;">SkillManager</div>
            </div>
            <div class="info-section">
                <h3>Dados do estagiário</h3>
                <p><strong>Nome:</strong> ${nomeAluno} &nbsp;|&nbsp; <strong>Empresa:</strong> ${nomeEmpresa}</p>
                ${dadosEstagio ? `<p style="font-size:13px; margin-top:5px;"><strong>Contrato:</strong> ${dadosEstagio.titulo} (${formatarData(dadosEstagio.dataInicio)} - ${formatarData(dadosEstagio.dataTermino)})</p>` : ''}
                <div style="margin-top: 15px;"><strong style="font-size:13px; text-transform:uppercase; color:#4A5568;">Competências atuais:</strong><div class="comp-container">${htmlCompetencias}</div></div>
            </div>
            <h3 style="color: #2C5282; border-left: 5px solid #F47C2A; padding-left: 10px; margin-bottom: 20px;">Linha do tempo das avaliações</h3>
            ${htmlAvaliacoes}
            <div class="footer">Documento gerado em ${new Date().toLocaleDateString()}.</div>
            <div style="text-align:center; margin-top:30px;" class="no-print"><button onclick="window.print()" style="padding:12px 24px; background:#2C5282; color:white; border:none; border-radius:6px; cursor:pointer; font-weight:600;">Imprimir histórico completo</button></div>
        </div>
    </body>
    </html>
    `;
};