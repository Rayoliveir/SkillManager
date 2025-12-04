// --- CONFIGURAÇÃO DE URL INTELIGENTE ---
// Se o site estiver rodando no Render (produção), usa a URL da nuvem.
// Se estiver rodando no seu PC (desenvolvimento), usa localhost.
const API_URL = process.env.NODE_ENV === 'production' 
    ? 'https://skillmanager-api.onrender.com' 
    : 'http://localhost:8080';

const apiFetch = async (endpoint, options = {}) => {
    const token = localStorage.getItem('authToken');
    const config = {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...options.headers,
        },
    };

    if (token) {
        config.headers['Authorization'] = `Basic ${token}`;
    }

    const response = await fetch(`${API_URL}${endpoint}`, config);

    if (!response.ok) {
        try {
            const errorData = await response.json();
            throw new Error(errorData.mensagem || `Erro ${response.status}`);
        } catch (jsonError) {
            throw new Error(`Erro ${response.status}: ${response.statusText}`);
        }
    }
    
    const text = await response.text();
    return text ? JSON.parse(text) : {};
};


// --- Autenticação ---
export const login = async (username, password) => {
    // IMPORTANTE: Codifica para Base64 corretamente (com tratamento de UTF-8 se precisar)
    const token = btoa(`${username}:${password}`);
    
    console.log("Tentando logar em:", `${API_URL}/login`); // LOG DE DEBUG

    try {
        const response = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: { 
                'Authorization': `Basic ${token}`, 
                'Content-Type': 'application/json' 
            },
        });

        if (!response.ok) {
            // Tenta ler o erro como JSON, se falhar lê como Texto
            const errorText = await response.text();
            console.error("Erro do servidor:", response.status, errorText); // LOG DE ERRO REAL
            
            try {
                const errorJson = JSON.parse(errorText);
                throw new Error(errorJson.mensagem || `Erro ${response.status}: ${response.statusText}`);
            } catch (e) {
                throw new Error(`Erro ${response.status}: ${response.statusText}`);
            }
        }

        const data = await response.json();
        // Salva token e dados
        localStorage.setItem('authToken', token);
        localStorage.setItem('user', JSON.stringify(data));
        return data;

    } catch (error) {
        console.error("Falha na requisição:", error);
        throw error;
    }
};


export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
};

// --- Cadastros ---
export const cadastrarEstagiario = async (data) => apiFetch('/estagiarios', { method: 'POST', body: JSON.stringify(data) });
export const cadastrarSupervisor = async (data) => apiFetch('/supervisores', { method: 'POST', body: JSON.stringify(data) });
export const cadastrarCoordenador = async (data) => apiFetch('/coordenadores', { method: 'POST', body: JSON.stringify(data) });
export const cadastrarFaculdade = async (data) => apiFetch('/faculdades', { method: 'POST', body: JSON.stringify(data) });
export const enviarEmailContato = async (data) => apiFetch('/contato', { method: 'POST', body: JSON.stringify(data) });

// --- Leitura ---
export const getDashboardData = async () => apiFetch('/dashboard', { method: 'GET' });
export const getEstagiarioDetails = async (id) => apiFetch(`/estagiarios/${id}`, { method: 'GET' });
export const getCompetencias = async (estagiarioId) => apiFetch(`/competencias/estagiario/${estagiarioId}`, { method: 'GET' });

// --- NOVO: Histórico Completo ---
export const getHistoricoAvaliacoes = async (estagiarioId) => apiFetch(`/avaliacoes/estagiario/${estagiarioId}`, { method: 'GET' });

// --- Atualizações ---
export const criarAvaliacao = async (data) => apiFetch('/avaliacoes', { method: 'POST', body: JSON.stringify(data) });
export const atualizarSupervisor = async (id, data) => apiFetch(`/supervisores/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const atualizarEstagiario = async (id, data) => apiFetch(`/estagiarios/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const atualizarCoordenador = async (id, data) => apiFetch(`/coordenadores/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const excluirEstagiario = async (id) => apiFetch(`/estagiarios/${id}`, { method: 'DELETE' });
export const atualizarAvaliacao = async (id, data) => apiFetch(`/avaliacoes/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const excluirAvaliacao = async (id) => apiFetch(`/avaliacoes/${id}`, { method: 'DELETE' });

// --- Competências ---
export const adicionarCompetencia = async (data) => apiFetch('/competencias', { method: 'POST', body: JSON.stringify(data) });
export const removerCompetencia = async (id) => apiFetch(`/competencias/${id}`, { method: 'DELETE' });

// --- DADOS DO ESTÁGIO (Novo - Supervisor) ---
export const salvarDadosEstagio = async (data) => apiFetch('/dados-estagio', { method: 'POST', body: JSON.stringify(data) });
export const atualizarDadosEstagio = async (id, data) => apiFetch(`/dados-estagio/${id}`, { method: 'PUT', body: JSON.stringify(data) });