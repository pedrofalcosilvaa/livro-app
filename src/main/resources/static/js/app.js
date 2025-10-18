const base = "/livros";

async function fetchJson(url, opts) {
  const res = await fetch(url, opts);
  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const body = isJson ? await res.json() : null;
  if (!res.ok) {
    throw body || {message: 'Erro desconhecido'};
  }
  return body;
}

async function listar() {
  try {
    const livros = await fetchJson(base);
    const tbody = document.querySelector("#tabelaLivros tbody");
    tbody.innerHTML = "";
    livros.forEach(l => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${l.id}</td>
        <td>${escapeHtml(l.titulo)}</td>
        <td>${l.qtdPaginas || ""}</td>
        <td>${l.publicacao?.autor || ""}</td>
        <td>${l.publicacao?.dataPublicacao || ""}</td>
        <td>${l.publicacao?.editora || ""}</td>
        <td>
          <button data-id="${l.id}" class="edit">Editar</button>
          <button data-id="${l.id}" class="del">Excluir</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    showMensagem(err.message || JSON.stringify(err), "error");
  }
}

function escapeHtml(s){ if(!s) return ""; return s.replace(/[&<>"']/g, c=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;' })[c]); }

function showMensagem(msg, tipo="ok"){
  const el = document.getElementById("mensagem");
  el.textContent = msg;
  el.className = "mensagem " + (tipo==="error"?"error":"ok");
  setTimeout(()=>{ el.textContent=""; el.className="mensagem"; }, 4000);
}

document.getElementById("formLivro").addEventListener("submit", async (e)=>{
  e.preventDefault();
  const id = document.getElementById("livroId").value;
  const payload = {
    titulo: document.getElementById("titulo").value.trim(),
    qtdPaginas: parseInt(document.getElementById("qtdPaginas").value) || null,
    publicacao: {
      autor: document.getElementById("autor").value.trim(),
      dataPublicacao: document.getElementById("dataPublicacao").value || null,
      editora: document.getElementById("editora").value.trim() || null
    }
  };
  try {
    if (id) {
      await fetchJson(base + "/" + id, { method: "PUT", headers: {"Content-Type":"application/json"}, body: JSON.stringify(payload) });
      showMensagem("Livro atualizado com sucesso");
    } else {
      await fetchJson(base, { method: "POST", headers: {"Content-Type":"application/json"}, body: JSON.stringify(payload) });
      showMensagem("Livro criado com sucesso");
    }
    limparForm();
    listar();
  } catch (err) {
    const msg = err?.message || (err?.errors ? err.errors.join("; ") : JSON.stringify(err));
    showMensagem(msg, "error");
  }
});

document.getElementById("limpar").addEventListener("click", (e)=>{ e.preventDefault(); limparForm(); });

function limparForm(){
  document.getElementById("livroId").value = "";
  document.getElementById("titulo").value = "";
  document.getElementById("qtdPaginas").value = "";
  document.getElementById("autor").value = "";
  document.getElementById("dataPublicacao").value = "";
  document.getElementById("editora").value = "";
}

document.querySelector("#tabelaLivros tbody").addEventListener("click", async (e)=>{
  if (e.target.matches(".edit")) {
    const id = e.target.dataset.id;
    const l = await fetchJson(base + "/" + id);
    document.getElementById("livroId").value = l.id;
    document.getElementById("titulo").value = l.titulo || "";
    document.getElementById("qtdPaginas").value = l.qtdPaginas || "";
    document.getElementById("autor").value = l.publicacao?.autor || "";
    document.getElementById("dataPublicacao").value = l.publicacao?.dataPublicacao || "";
    document.getElementById("editora").value = l.publicacao?.editora || "";
  } else if (e.target.matches(".del")) {
    const id = e.target.dataset.id;
    if (!confirm("Confirma exclusão do livro id " + id + " ?")) return;
    try {
      await fetchJson(base + "/" + id, { method: "DELETE" });
      showMensagem("Livro excluído");
      listar();
    } catch (err) {
      showMensagem(err.message || JSON.stringify(err), "error");
    }
  }
});

window.addEventListener("load", ()=>{ listar(); });
