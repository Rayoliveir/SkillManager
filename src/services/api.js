// --- MUDANÇA: O API_URL agora é apenas a base
const API_URL = 'http://localhost:8080';

// --- NOVO: Esta é a nossa função "interceptor"
// Ela substitui o 'handleResponse' e faz todo o trabalho de autenticação e tratamento de erros.
const apiFetch = async (endpoint, options = {}) => {
    // Pega o token 'Basic' (que o login salvou) do localStorage
    const token = localStorage.getItem('authToken');
    
    const config = {
        ...options, // Permite que o chamador defina method, body, etc.
        headers: {
            'Content-Type': 'application/json',
            ...options.headers,
        },
    };

    // --- MUDANÇA: Adiciona o header de autenticação em TODAS as chamadas
    if (token) {
        config.headers['Authorization'] = `Basic ${token}`;
    }

    // Faz a chamada de rede
    const response = await fetch(`${API_URL}${endpoint}`, config);

    // --- MUDANÇA: Novo 'handleResponse' que lê o JSON de erro do back-end
    if (!response.ok) {
        try {
            // Tenta ler o JSON de erro (ex: { "mensagem": "Email já cadastrado." })
            const errorData = await response.json();
            // Lança um erro com a MENSAGEM DO BACK-END
            throw new Error(errorData.mensagem || `Erro ${response.status}`);
        } catch (jsonError) {
            // Se o corpo não for JSON, lança o erro de status
            throw new Error(`Erro ${response.status}: ${response.statusText}`);
        }
    }
    
    // Se a resposta foi OK (2xx), retorna o JSON
    const text = await response.text();
    return text ? JSON.parse(text) : {};
};

// --- MUDANÇA: Função de Login agora usa o 'handleResponse' de erro correto
export const login = async (username, password) => {
    // 1. Cria o token 'Basic' (ex: "email:senha" -> "Basic dXNlcm5hbWU6...")
    const token = btoa(`${username}:${password}`);

    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Authorization': `Basic ${token}`,
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        // --- MUDANÇA: Tratamento de erro específico para 401 (Credenciais Inválidas)
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.mensagem || 'Email ou senha inválidos.');
    }

    const data = await response.json();
    
    // --- MUDANÇA: A API agora é responsável por guardar os dados de auth
    localStorage.setItem('authToken', token); // Guarda o token Basic (ex: "dXNlcm5hbWU6...")
    localStorage.setItem('user', JSON.stringify(data)); // Guarda o objeto { username, roles }
    
    return data;
};

// --- MUDANÇA: A API limpa o localStorage
export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
};

// --- Endpoints de Cadastro (Públicos) ---

export const cadastrarEstagiario = async (estagiarioData) => {
    // Usa o novo 'apiFetch' (não precisa de auth)
    return apiFetch('/estagiarios', {
        method: 'POST',
        body: JSON.stringify(estagiarioData),
    });
};

export const cadastrarSupervisor = async (supervisorData) => {
    // --- CORREÇÃO: Aponta para o endpoint correto /supervisores
    return apiFetch('/supervisores', {
        method: 'POST',
        body: JSON.stringify(supervisorData),
    });
};

export const cadastrarCoordenador = async (coordenadorData) => {
    // --- CORREÇÃO: Aponta para o endpoint correto /coordenadores
    return apiFetch('/coordenadores', {
        method: 'POST',
        body: JSON.stringify(coordenadorData),
    });
};

export const cadastrarFaculdade = async (faculdadeData) => {
    return apiFetch('/faculdades', {
        method: 'POST',
        body: JSON.stringify(faculdadeData),
    });
};

// --- Endpoints Protegidos (Autenticação Automática) ---

export const getDashboardData = async () => {
    // --- MUDANÇA: "Fluxo 1" - Pega os dados principais do dashboard ---
    return apiFetch('/dashboard', {
        method: 'GET',
    });
};

// --- NOVO: "Fluxo 2" ---
// Pega os detalhes completos (incluindo feedbacks) de um estagiário específico
export const getEstagiarioDetails = async (id) => {
    return apiFetch(`/estagiarios/${id}`, {
        method: 'GET',
    });
};

// --- NOVAS FUNÇÕES PARA ESCRITA ---

export const criarAvaliacao = async (avaliacaoData) => {
    return apiFetch('/avaliacoes', {
        method: 'POST',
        body: JSON.stringify(avaliacaoData),
    });
};

export const atualizarSupervisor = async (id, supervisorData) => {
    return apiFetch(`/supervisores/${id}`, {
        method: 'PUT',
        body: JSON.stringify(supervisorData),
    });
};

export const excluirEstagiario = async (id) => {
    return apiFetch(`/estagiarios/${id}`, {
        method: 'DELETE',
    });
};