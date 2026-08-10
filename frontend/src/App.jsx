import { useCallback, useEffect, useMemo, useState } from "react";

const page = window.location.pathname === "/" || window.location.pathname === "/home"
    ? "home"
    : window.location.pathname.replace("/", "") || "home";
const API_BASE_URL = (import.meta.env.VITE_API_URL || "").replace(/\/$/, "");

function getToken() {
    return localStorage.getItem("jwt");
}

function setToken(token) {
    localStorage.setItem("jwt", token);
}

function clearToken() {
    localStorage.removeItem("jwt");
}

async function api(path, options = {}) {
    const headers = { ...(options.headers || {}) };
    const token = getToken();

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    if (options.body !== undefined && !headers["Content-Type"]) {
        headers["Content-Type"] = "application/json";
    }

    const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers });

    if (!response.ok) {
        let message = response.statusText || "Request failed";
        try {
            const data = await response.clone().json();
            message = data.error || data.message || message;
        } catch (error) {
            const text = await response.text().catch(() => "");
            message = text || message;
        }
        throw new Error(message);
    }

    if (response.status === 204) {
        return null;
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function normalizeQuestions(data) {
    return data?.questionList || data?.question_list || [];
}

function useToast() {
    const [message, setMessage] = useState("");

    const showMessage = useCallback((text) => {
        setMessage(text);
        window.setTimeout(() => setMessage(""), 2400);
    }, []);

    return { message, showMessage };
}

function Header({ profile, onLogout }) {
    const isAuthenticated = Boolean(profile.authenticated);
    const isAdmin = Boolean(profile.admin);

    return (
        <header className="app-header">
            <a className="brand-block" href="/">
                <div className="logo-mark">SQ</div>
                <div>
                    <div className="eyebrow">Quiz platform</div>
                    <h1>Spring Quiz</h1>
                    <p>{page === "admin" ? "Управление контентом." : page === "login" ? "Вход в аккаунт." : page === "register" ? "Создание аккаунта." : "Платформа для тестов."}</p>
                </div>
            </a>
            <nav className="top-nav">
                <a className={page === "quizzes" ? "active" : ""} href="/quizzes">Тесты</a>
                {!isAuthenticated && page !== "login" && <a href="/login">Вход</a>}
                {!isAuthenticated && page !== "register" && <a href="/register">Регистрация</a>}
                {isAdmin && <a className={page === "admin" ? "active" : ""} href="/admin">Админка</a>}
                {isAuthenticated && <button className="ghost" type="button" onClick={onLogout}>Выйти</button>}
            </nav>
        </header>
    );
}

function App() {
    const { message, showMessage } = useToast();
    const [profile, setProfile] = useState({ authenticated: false, loading: true });

    const loadProfile = useCallback(async () => {
        if (!getToken()) {
            setProfile({ authenticated: false, loading: false });
            return;
        }

        try {
            const data = await api("/api/profile");
            setProfile({ ...data, authenticated: true, loading: false });
        } catch (error) {
            clearToken();
            setProfile({ authenticated: false, loading: false });
        }
    }, []);

    useEffect(() => {
        loadProfile();
    }, [loadProfile]);

    useEffect(() => {
        if (profile.loading) {
            return;
        }
        if (page === "admin" && !profile.admin) {
            window.location.href = profile.authenticated ? "/quizzes" : "/login";
        }
        if ((page === "login" || page === "register") && profile.authenticated) {
            window.location.href = profile.admin ? "/admin" : "/quizzes";
        }
    }, [profile]);

    const logout = () => {
        clearToken();
        window.location.href = "/login";
    };

    const content = useMemo(() => {
        if (page === "login") {
            return <LoginPage showMessage={showMessage} />;
        }
        if (page === "register") {
            return <RegisterPage showMessage={showMessage} />;
        }
        if (page === "quizzes") {
            return <QuizzesPage showMessage={showMessage} />;
        }
        if (page === "admin") {
            return profile.admin ? <AdminPage showMessage={showMessage} /> : <main className="page-shell"><div className="notice">Проверка доступа...</div></main>;
        }
        return <HomePage profile={profile} />;
    }, [profile, showMessage]);

    return (
        <>
            <Header profile={profile} onLogout={logout} />
            {content}
            {message && <div className="toast">{message}</div>}
        </>
    );
}

function HomePage({ profile }) {
    return (
        <main className="page-shell">
            <section className="hero-content">
                <h1>Spring Quiz</h1>
                <p>Проходите тесты отдельно от админки и страницы входа. Интерфейс собран на React и работает с текущим Spring Boot API.</p>
                <div className="hero-actions">
                    <a className="button-link" href="/quizzes">Открыть тесты</a>
                    {!profile.authenticated && <a className="button-link secondary" href="/login">Войти</a>}
                    {profile.admin && <a className="button-link secondary" href="/admin">Админка</a>}
                </div>
            </section>
        </main>
    );
}

function LoginPage({ showMessage }) {
    const [login, setLogin] = useState({ username: "", password: "" });

    const submitLogin = async (event) => {
        event.preventDefault();
        try {
            const data = await api("/api/auth/login", {
                method: "POST",
                body: JSON.stringify(login)
            });
            setToken(data.token);
            window.location.href = "/quizzes";
        } catch (error) {
            showMessage(error.message);
        }
    };

    return (
        <main className="page-shell narrow">
            <section className="view-header">
                <div>
                    <h2>Вход</h2>
                    <p>Войдите в существующий аккаунт.</p>
                </div>
            </section>
            <form className="panel stack-form" onSubmit={submitLogin}>
                <h3>Вход</h3>
                <div className="demo-credentials">
                    <p><strong>student</strong> / student123</p>
                    <p><strong>admin</strong> / admin123</p>
                </div>
                <label>Логин
                    <input autoComplete="username" required value={login.username} onChange={(event) => setLogin({ ...login, username: event.target.value })} />
                </label>
                <label>Пароль
                    <input type="password" autoComplete="current-password" required value={login.password} onChange={(event) => setLogin({ ...login, password: event.target.value })} />
                </label>
                <div className="button-row">
                    <button type="submit">Войти</button>
                    <a className="button-link secondary" href="/register">Создать аккаунт</a>
                </div>
            </form>
        </main>
    );
}

function RegisterPage({ showMessage }) {
    const [register, setRegister] = useState({ username: "", password: "", passwordConfirm: "" });

    const submitRegister = async (event) => {
        event.preventDefault();
        try {
            const data = await api("/api/auth/register", {
                method: "POST",
                body: JSON.stringify(register)
            });
            setToken(data.token);
            window.location.href = "/quizzes";
        } catch (error) {
            showMessage(error.message);
        }
    };

    return (
        <main className="page-shell narrow">
            <section className="view-header">
                <div>
                    <h2>Регистрация</h2>
                    <p>Создайте новый аккаунт.</p>
                </div>
            </section>
            <form className="panel stack-form" onSubmit={submitRegister}>
                <h3>Регистрация</h3>
                <label>Логин
                    <input autoComplete="username" required value={register.username} onChange={(event) => setRegister({ ...register, username: event.target.value })} />
                </label>
                <label>Пароль
                    <input type="password" autoComplete="new-password" required value={register.password} onChange={(event) => setRegister({ ...register, password: event.target.value })} />
                </label>
                <label>Повтор пароля
                    <input type="password" autoComplete="new-password" required value={register.passwordConfirm} onChange={(event) => setRegister({ ...register, passwordConfirm: event.target.value })} />
                </label>
                <div className="button-row">
                    <button type="submit">Зарегистрироваться</button>
                    <a className="button-link secondary" href="/login">Уже есть аккаунт</a>
                </div>
            </form>
        </main>
    );
}

function QuizzesPage({ showMessage }) {
    const [quizzes, setQuizzes] = useState([]);
    const [search, setSearch] = useState("");
    const [quiz, setQuiz] = useState(null);
    const [selectedAnswers, setSelectedAnswers] = useState({});
    const [result, setResult] = useState(null);

    const loadQuizzes = useCallback(async () => {
        try {
            setQuizzes(await api("/api/quizzes"));
        } catch (error) {
            showMessage(error.message);
        }
    }, [showMessage]);

    useEffect(() => {
        loadQuizzes();
    }, [loadQuizzes]);

    const filteredQuizzes = quizzes.filter((item) => {
        return !search || item.subjectTitle.toLowerCase().includes(search.toLowerCase());
    });

    const startQuiz = async (subjectId) => {
        if (!getToken()) {
            window.location.href = "/login";
            return;
        }
        try {
            const data = await api(`/api/quizzes/${subjectId}`);
            setQuiz(data);
            setSelectedAnswers({});
            setResult(null);
        } catch (error) {
            showMessage(error.message);
        }
    };

    const submitAnswer = async (questionId, answerId) => {
        try {
            await api("/api/quizzes/answers", {
                method: "POST",
                body: JSON.stringify({ subjectId: quiz.subjectId, questionId, answerId })
            });
            setSelectedAnswers({ ...selectedAnswers, [questionId]: answerId });
        } catch (error) {
            showMessage(error.message);
        }
    };

    const finishQuiz = async () => {
        if (!quiz) {
            return;
        }
        try {
            const data = await api(`/api/quizzes/${quiz.subjectId}/results`, { method: "POST" });
            setResult(data);
            await loadQuizzes();
        } catch (error) {
            showMessage(error.message);
        }
    };

    const questions = quiz?.questionAnswers || [];
    const answeredCount = Object.keys(selectedAnswers).length;
    const progress = questions.length ? `${answeredCount / questions.length * 100}%` : "0%";

    return (
        <main className="page-shell">
            <section className="view-header">
                <div>
                    <h2>Тесты</h2>
                    <p>Выберите тест, ответьте на вопросы и завершите попытку.</p>
                </div>
                <div className="toolbar">
                    <input className="search-input" placeholder="Найти тест" value={search} onChange={(event) => setSearch(event.target.value)} />
                    <button className="secondary" type="button" onClick={loadQuizzes}>Обновить</button>
                </div>
            </section>
            <section className="quiz-layout">
                <div className="panel">
                    <div className="panel-heading">
                        <div>
                            <h3>Доступные тесты</h3>
                            <p>Показываются только активные предметы.</p>
                        </div>
                    </div>
                    <div className="cards-grid">
                        {filteredQuizzes.length === 0 && <div className="notice">Активных тестов пока нет.</div>}
                        {filteredQuizzes.map((item) => (
                            <div className="quiz-card" key={item.subjectId}>
                                <div className="item-row">
                                    <div>
                                        <div className="item-title">{item.subjectTitle}</div>
                                        <p className="card-subtitle">Одна попытка на прохождение</p>
                                    </div>
                                    <span className={`badge ${item.canPass ? "" : "muted"}`}>{item.canPass ? "Доступен" : "Пройден"}</span>
                                </div>
                                <div className="quiz-card-actions">
                                    <button type="button" disabled={!item.canPass} onClick={() => startQuiz(item.subjectId)}>
                                        {item.canPass ? "Начать" : "Уже пройден"}
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
                <div className="panel quiz-panel">
                    {quiz && (
                        <div className="quiz-progress">
                            <div className="progress-copy">{answeredCount}/{questions.length} отвечено</div>
                            <div className="progress-track"><div id="progressBar" style={{ width: progress }}></div></div>
                        </div>
                    )}
                    {!quiz && (
                        <div className="empty-state">
                            <h3>Выберите тест</h3>
                            <p>Вопросы появятся после старта.</p>
                        </div>
                    )}
                    {quiz && (
                        <>
                            <div className="panel-heading">
                                <div>
                                    <h3>{quiz.subjectTitle}</h3>
                                    <p>{questions.length} вопросов</p>
                                </div>
                            </div>
                            {questions.map((question, index) => (
                                <div className="question" key={question.questionId}>
                                    <h3>{index + 1}. {question.questionTitle}</h3>
                                    {question.answers.map((answer) => (
                                        <label className="answer" key={answer.answerId}>
                                            <input
                                                type="radio"
                                                name={`q-${question.questionId}`}
                                                checked={selectedAnswers[question.questionId] === answer.answerId}
                                                onChange={() => submitAnswer(question.questionId, answer.answerId)}
                                            />
                                            <span>{answer.answerTitle}</span>
                                        </label>
                                    ))}
                                </div>
                            ))}
                            <div className="quiz-footer">
                                <button type="button" disabled={Boolean(result)} onClick={finishQuiz}>Завершить тест</button>
                            </div>
                            {result && <div className="result-card">Результат: {result.quizScore}</div>}
                        </>
                    )}
                </div>
            </section>
        </main>
    );
}

function AdminPage({ showMessage }) {
    const createEmptyQuestionAnswers = () => [
        { title: "", correct: true },
        { title: "", correct: false }
    ];
    const [subjects, setSubjects] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [selectedQuestion, setSelectedQuestion] = useState(null);
    const [answers, setAnswers] = useState([]);
    const [subjectForm, setSubjectForm] = useState({ id: "", title: "", isActive: true });
    const [questionForm, setQuestionForm] = useState({ id: "", title: "", points: 1 });
    const [questionAnswers, setQuestionAnswers] = useState(createEmptyQuestionAnswers);
    const [isQuestionModalOpen, setQuestionModalOpen] = useState(false);
    const [answerTitle, setAnswerTitle] = useState("");
    const [answerForm, setAnswerForm] = useState({ id: "", title: "" });

    const loadSubjects = useCallback(async () => {
        try {
            setSubjects(await api("/api/subjects"));
        } catch (error) {
            showMessage(error.message);
        }
    }, [showMessage]);

    useEffect(() => {
        loadSubjects();
    }, [loadSubjects]);

    const resetSubjectForm = () => setSubjectForm({ id: "", title: "", isActive: true });
    const resetQuestionForm = () => {
        setQuestionForm({ id: "", title: "", points: 1 });
        setQuestionAnswers(createEmptyQuestionAnswers());
    };
    const openCreateQuestionModal = () => {
        if (!selectedSubject) {
            showMessage("Сначала выберите предмет");
            return;
        }
        resetQuestionForm();
        setQuestionModalOpen(true);
    };
    const openEditQuestionModal = (question) => {
        setQuestionForm({ id: question.id, title: question.title, points: question.points });
        setQuestionModalOpen(true);
    };
    const closeQuestionModal = () => {
        setQuestionModalOpen(false);
        resetQuestionForm();
    };
    const clearAnswers = () => {
        setSelectedQuestion(null);
        setAnswers([]);
        setAnswerTitle("");
        setAnswerForm({ id: "", title: "" });
    };

    const selectSubject = async (subjectId) => {
        const subject = subjects.find((item) => String(item.id) === subjectId);
        if (subject) {
            await loadQuestions(subject);
        } else {
            setSelectedSubject(null);
            setQuestions([]);
            clearAnswers();
        }
    };

    const loadQuestions = async (subject) => {
        try {
            const data = await api(`/api/subjects/${subject.id}/questions`);
            setSelectedSubject(subject);
            setQuestions(normalizeQuestions(data));
            resetQuestionForm();
            clearAnswers();
        } catch (error) {
            showMessage(error.message);
        }
    };

    const loadAnswers = async (question) => {
        try {
            const data = await api(`/api/questions/${question.id}/answers`);
            setSelectedQuestion(question);
            setAnswers(data?.answerList || data?.answer_list || []);
            setAnswerForm({ id: "", title: "" });
        } catch (error) {
            showMessage(error.message);
        }
    };

    const saveSubject = async (event) => {
        event.preventDefault();
        const id = subjectForm.id;
        try {
            await api(id ? `/api/subjects/${id}` : "/api/subjects", {
                method: id ? "PUT" : "POST",
                body: JSON.stringify({ title: subjectForm.title, isActive: subjectForm.isActive })
            });
            resetSubjectForm();
            await loadSubjects();
            showMessage("Предмет сохранён");
        } catch (error) {
            showMessage(error.message);
        }
    };

    const deleteSubject = async (subject) => {
        if (!window.confirm("Удалить предмет вместе с вопросами?")) {
            return;
        }
        try {
            await api(`/api/subjects/${subject.id}`, { method: "DELETE" });
            setSelectedSubject(null);
            setQuestions([]);
            clearAnswers();
            await loadSubjects();
        } catch (error) {
            showMessage(error.message);
        }
    };

    const saveQuestion = async (event) => {
        event.preventDefault();
        if (!selectedSubject) {
            showMessage("Сначала выберите предмет");
            return;
        }
        const id = questionForm.id;
        const nextAnswers = questionAnswers
            .map((answer) => ({ answerTitle: answer.title.trim(), correct: answer.correct }))
            .filter((answer) => answer.answerTitle);
        if (!id && nextAnswers.length < 2) {
            showMessage("Добавьте минимум два ответа");
            return;
        }
        if (!id && !nextAnswers.some((answer) => answer.correct)) {
            showMessage("Отметьте правильный ответ");
            return;
        }
        try {
            await api(id ? `/api/questions/${id}` : `/api/subjects/${selectedSubject.id}/questions`, {
                method: id ? "PUT" : "POST",
                body: JSON.stringify({ title: questionForm.title, points: Number(questionForm.points), answers: nextAnswers })
            });
            const currentSubject = selectedSubject;
            closeQuestionModal();
            await loadQuestions(currentSubject);
            showMessage("Вопрос сохранён");
        } catch (error) {
            showMessage(error.message);
        }
    };

    const updateQuestionAnswer = (index, changes) => {
        setQuestionAnswers(questionAnswers.map((answer, answerIndex) => answerIndex === index ? { ...answer, ...changes } : answer));
    };

    const setQuestionCorrectAnswer = (index) => {
        setQuestionAnswers(questionAnswers.map((answer, answerIndex) => ({ ...answer, correct: answerIndex === index })));
    };

    const addQuestionAnswer = () => {
        setQuestionAnswers([...questionAnswers, { title: "", correct: false }]);
    };

    const removeQuestionAnswer = (index) => {
        if (questionAnswers.length <= 2) {
            showMessage("У вопроса должно быть минимум два ответа");
            return;
        }
        const nextAnswers = questionAnswers.filter((answer, answerIndex) => answerIndex !== index);
        if (!nextAnswers.some((answer) => answer.correct)) {
            nextAnswers[0] = { ...nextAnswers[0], correct: true };
        }
        setQuestionAnswers(nextAnswers);
    };

    const deleteQuestion = async (question) => {
        try {
            await api(`/api/questions/${question.id}`, { method: "DELETE" });
            clearAnswers();
            const currentSubject = selectedSubject;
            await loadQuestions(currentSubject);
        } catch (error) {
            showMessage(error.message);
        }
    };

    const saveAnswer = async () => {
        if (!selectedQuestion) {
            showMessage("Сначала выберите вопрос");
            return;
        }
        if (!answerTitle.trim()) {
            showMessage("Введите текст ответа");
            return;
        }
        try {
            await api(`/api/questions/${selectedQuestion.id}/answers`, {
                method: "POST",
                body: JSON.stringify({ answerTitle: answerTitle.trim() })
            });
            setAnswerTitle("");
            await loadAnswers(selectedQuestion);
            showMessage("Ответ добавлен");
        } catch (error) {
            showMessage(error.message);
        }
    };

    const startEditAnswer = (answer) => {
        setAnswerForm({ id: answer.answerId, title: answer.answerTitle });
    };

    const cancelEditAnswer = () => {
        setAnswerForm({ id: "", title: "" });
    };

    const updateAnswer = async () => {
        if (!answerForm.title.trim()) {
            showMessage("Введите текст ответа");
            return;
        }
        try {
            await api(`/api/answers/${answerForm.id}`, {
                method: "PUT",
                body: JSON.stringify({ answerTitle: answerForm.title.trim() })
            });
            cancelEditAnswer();
            await loadAnswers(selectedQuestion);
            showMessage("Ответ сохранён");
        } catch (error) {
            showMessage(error.message);
        }
    };

    const setCorrectAnswer = async (answer) => {
        try {
            await api(`/api/answers/${answer.answerId}/correct`, { method: "PUT" });
            await loadAnswers(selectedQuestion);
        } catch (error) {
            showMessage(error.message);
        }
    };

    const deleteAnswer = async (answer) => {
        if (answers.length <= 2) {
            showMessage("У вопроса должно быть минимум два ответа");
            return;
        }
        if (answer.correct) {
            showMessage("Сначала назначьте правильным другой ответ");
            return;
        }
        try {
            await api(`/api/answers/${answer.answerId}`, { method: "DELETE" });
            await loadAnswers(selectedQuestion);
        } catch (error) {
            showMessage(error.message);
        }
    };

    return (
        <main className="page-shell">
            <section className="view-header">
                <div>
                    <h2>Админка</h2>
                    <p>Предметы, вопросы и варианты ответов редактируются отдельно от прохождения тестов.</p>
                </div>
                <button className="secondary" type="button" onClick={loadSubjects}>Обновить</button>
            </section>

            <section className="admin-layout">
                <div className="panel">
                    <div className="panel-heading">
                        <div>
                            <h3>Предметы</h3>
                            <p>Создайте категорию теста.</p>
                        </div>
                    </div>
                    <form className="stack-form" onSubmit={saveSubject}>
                        <label>Название
                            <input required placeholder="Java basics" value={subjectForm.title} onChange={(event) => setSubjectForm({ ...subjectForm, title: event.target.value })} />
                        </label>
                        <label className="check-row">
                            <input type="checkbox" checked={subjectForm.isActive} onChange={(event) => setSubjectForm({ ...subjectForm, isActive: event.target.checked })} />
                            Активный тест
                        </label>
                        <div className="button-row">
                            <button type="submit">Сохранить</button>
                            <button type="button" className="ghost" onClick={resetSubjectForm}>Очистить</button>
                        </div>
                    </form>
                    <div className="list">
                        {subjects.length === 0 && <div className="notice">Предметов пока нет.</div>}
                        {subjects.map((subject) => (
                            <div className="item" key={subject.id}>
                                <div className="item-row">
                                    <div>
                                        <div className="item-title">{subject.title}</div>
                                        <p className="card-subtitle">Контент предмета</p>
                                        <span className={`badge ${subject.isActive ? "" : "muted"}`}>{subject.isActive ? "Активен" : "Скрыт"}</span>
                                    </div>
                                    <div className="button-row">
                                        <button type="button" className="secondary" onClick={() => setSubjectForm({ id: subject.id, title: subject.title, isActive: subject.isActive })}>Изменить</button>
                                        <button type="button" className="danger" onClick={() => deleteSubject(subject)}>Удалить</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                <div className="panel quiz-editor">
                    <div className="panel-heading">
                        <div>
                            <h3>Вопросы и ответы</h3>
                            <p>Выберите предмет, добавьте вопрос и сразу настройте варианты ответа.</p>
                        </div>
                    </div>
                    <label>Выберите предмет
                        <select value={selectedSubject?.id || ""} onChange={(event) => selectSubject(event.target.value)}>
                            <option value="">Сначала выберите предмет</option>
                            {subjects.map((subject) => (
                                <option value={subject.id} key={subject.id}>{subject.title}</option>
                            ))}
                        </select>
                    </label>
                    <div className="editor-grid">
                        <div>
                            <div className="toolbar">
                                <button type="button" onClick={openCreateQuestionModal}>Добавить вопрос</button>
                            </div>
                            <div className="list">
                                {selectedSubject && questions.length === 0 && <div className="notice">Вопросов пока нет.</div>}
                                {questions.map((question) => (
                                    <div className={`item question-card ${selectedQuestion?.id === question.id ? "active" : ""}`} key={question.id} onClick={() => loadAnswers(question)}>
                                        <div className="item-row">
                                            <div>
                                                <div className="item-title">{question.title}</div>
                                                <p className="card-subtitle">Настройка вопроса</p>
                                                <span className="badge">{question.points} балл.</span>
                                            </div>
                                            <div className="button-row">
                                                <button type="button" className="secondary" onClick={(event) => {
                                                    event.stopPropagation();
                                                    openEditQuestionModal(question);
                                                }}>Изменить</button>
                                                <button type="button" className="danger" onClick={(event) => {
                                                    event.stopPropagation();
                                                    deleteQuestion(question);
                                                }}>Удалить</button>
                                            </div>
                                        </div>
                                        {selectedQuestion?.id === question.id && (
                                            <div className="question-answers" onClick={(event) => event.stopPropagation()}>
                                                <div className="answers-heading">
                                                    <div>
                                                        <strong>Варианты ответов</strong>
                                                        <p className="card-subtitle">Ответы относятся к выбранному вопросу выше.</p>
                                                    </div>
                                                </div>
                                                <div className="inline-form">
                                                    <input
                                                        placeholder="Новый вариант ответа"
                                                        value={answerTitle}
                                                        onChange={(event) => setAnswerTitle(event.target.value)}
                                                        onKeyDown={(event) => {
                                                            if (event.key === "Enter") {
                                                                event.preventDefault();
                                                                saveAnswer();
                                                            }
                                                        }}
                                                    />
                                                    <button type="button" onClick={saveAnswer}>Добавить ответ</button>
                                                </div>
                                                <div className="list">
                                                    {answers.length === 0 && <div className="notice">Ответов пока нет.</div>}
                                                    {answers.map((answer) => (
                                                        <div className="answer-row" key={answer.answerId}>
                                                            {answerForm.id === answer.answerId ? (
                                                                <>
                                                                    <input
                                                                        value={answerForm.title}
                                                                        onChange={(event) => setAnswerForm({ ...answerForm, title: event.target.value })}
                                                                        onKeyDown={(event) => {
                                                                            if (event.key === "Enter") {
                                                                                event.preventDefault();
                                                                                updateAnswer();
                                                                            }
                                                                            if (event.key === "Escape") {
                                                                                cancelEditAnswer();
                                                                            }
                                                                        }}
                                                                    />
                                                                    <div className="button-row">
                                                                        <button type="button" onClick={updateAnswer}>Сохранить</button>
                                                                        <button type="button" className="ghost" onClick={cancelEditAnswer}>Отмена</button>
                                                                    </div>
                                                                </>
                                                            ) : (
                                                                <>
                                                                    <div>
                                                                        <div className="item-title">{answer.answerTitle}</div>
                                                                        <p className="card-subtitle">Вариант ответа</p>
                                                                    </div>
                                                                    <div className="button-row">
                                                                        {answer.correct ? <span className="badge" title="Правильный ответ">✓</span> : <button type="button" className="ghost icon-button" title="Сделать правильным" onClick={() => setCorrectAnswer(answer)}>✓</button>}
                                                                        <button type="button" className="secondary" onClick={() => startEditAnswer(answer)}>Изменить</button>
                                                                        <button type="button" className="danger" onClick={() => deleteAnswer(answer)}>Удалить</button>
                                                                    </div>
                                                                </>
                                                            )}
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {isQuestionModalOpen && (
                <div className="modal-backdrop" onClick={closeQuestionModal}>
                    <form className="modal-card stack-form" onSubmit={saveQuestion} onClick={(event) => event.stopPropagation()}>
                        <div className="panel-heading">
                            <div>
                                <h3>{questionForm.id ? "Изменить вопрос" : "Добавить вопрос"}</h3>
                                <p>Предмет: {selectedSubject?.title}</p>
                            </div>
                            <button type="button" className="ghost icon-button" onClick={closeQuestionModal}>×</button>
                        </div>
                        <label>Вопрос
                            <input required autoFocus placeholder="Что такое Spring Boot?" value={questionForm.title} onChange={(event) => setQuestionForm({ ...questionForm, title: event.target.value })} />
                        </label>
                        <label>Баллы
                            <input type="number" min="1" required value={questionForm.points} onChange={(event) => setQuestionForm({ ...questionForm, points: event.target.value })} />
                        </label>
                        {!questionForm.id && (
                            <div className="modal-answer-list">
                                <div>
                                    <strong>Ответы к вопросу</strong>
                                    <p className="card-subtitle">Минимум два ответа. Галочкой отметьте правильный.</p>
                                </div>
                                {questionAnswers.map((answer, index) => (
                                    <div className="modal-answer-row" key={index}>
                                        <input
                                            placeholder={`Ответ ${index + 1}`}
                                            required
                                            value={answer.title}
                                            onChange={(event) => updateQuestionAnswer(index, { title: event.target.value })}
                                        />
                                        <button type="button" className="ghost icon-button" title="Правильный ответ" onClick={() => setQuestionCorrectAnswer(index)}>
                                            {answer.correct ? "✓" : "○"}
                                        </button>
                                        <button type="button" className="danger icon-button" onClick={() => removeQuestionAnswer(index)}>×</button>
                                    </div>
                                ))}
                                <button type="button" className="ghost" onClick={addQuestionAnswer}>Добавить ещё ответ</button>
                            </div>
                        )}
                        <div className="button-row">
                            <button type="submit">Сохранить</button>
                            <button type="button" className="ghost" onClick={closeQuestionModal}>Отмена</button>
                        </div>
                    </form>
                </div>
            )}
        </main>
    );
}

export default App;
