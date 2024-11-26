document.addEventListener('DOMContentLoaded', function() {
    const quotes = [
        'When you have eliminated the impossible, whatever remains, however improbable, must be the truth.',
        'There is nothing more deceptive than an obvious fact.',
        'I ought to know by this time that when a fact appears to be opposed to a long train of deductions it invariably proves to be capable of bearing some other interpretation.',
        'I never make exceptions. An exception disproves the rule.',
        'What one man can invent another can discover.',
        'Nothing clears up a case so much as stating it to another person.',
        'Education never ends, Watson. It is a series of lessons, with the greatest for the last.',
		'This Time I Want You',
		'Shimmy shimmy cocoa puff'
    ];

    let currentQuote = '';
    let currentIndex = 0;
    let startTime = null;
    let timer = null;
    const timePerCharacter = 0.5; // 每个字符0.5秒

    const quoteElement = document.getElementById('quote');
    const messageElement = document.getElementById('message');
    const typedValueElement = document.getElementById('typed-value');
    const startButton = document.getElementById('start');

    // 生成随机样式
    function getRandomStyle() {
        const fonts = ["Arial", "Verdana", "Helvetica", "Times New Roman"];
        const colors = ["#0000FF", "#DC143C", "#4B0082", "#00FF00"];
        const sizes = ["20px", "22px", "24px"];
        
        const randomFont = fonts[Math.floor(Math.random() * fonts.length)];
        const randomColor = colors[Math.floor(Math.random() * colors.length)];
        const randomSize = sizes[Math.floor(Math.random() * sizes.length)];

        return `font-family: ${randomFont}; color: ${randomColor}; font-size: ${randomSize}; background-color: green;`;
    }

    // 单击按钮，开始游戏
    startButton.addEventListener('click', function() {
        const randomIndex = Math.floor(Math.random() * quotes.length);
        currentQuote = quotes[randomIndex];
        currentIndex = 0;
        renderQuote(currentQuote);
        messageElement.innerHTML = '';
        typedValueElement.value = '';
        typedValueElement.disabled = false;
        typedValueElement.focus();


        // 计算时间限制
        const timeLimit = currentQuote.length * timePerCharacter;

        startTime = new Date().getTime();

        if (timer) {
            clearTimeout(timer);
        }

        // 开始计时器
        timer = setTimeout(function() {
            typedValueElement.disabled = true;
            messageElement.innerHTML = 'Time is up!';
			alert("Opps!!!!")
			window.location.href = 'failure.html';//失败跳转
        }, timeLimit * 1000);
    });

    // 渲染quote并应用初始样式
    function renderQuote(quote) {
        quoteElement.innerHTML = ''; // 清空内容
        quote.split('').forEach((char, index) => {
            const span = document.createElement('span');
            span.textContent = char;
            span.style.cssText = 'font-family: Arial; font-size: 16px;'; // 初始样式
            quoteElement.appendChild(span);
        });
    }

    // 编辑框的输入处理
    typedValueElement.addEventListener('input', function() {
        const typedValue = typedValueElement.value;
        const quoteChars = quoteElement.querySelectorAll('span');

        quoteChars.forEach((char, index) => {
            if (index < typedValue.length) {
                if (typedValue[index] === char.textContent) {
                    char.classList.remove('error');
                    char.style.cssText = getRandomStyle();
                    // 移除背景色
                    setTimeout(() => {
                        char.style.backgroundColor = 'transparent';
                    }, 50); // 移除背景色
                } else {
                    char.classList.add('error');
                    char.style.cssText = 'font-family: SimSun, serif; font-weight: bold; background-color: red; color: black;';
                }
            } else {
                char.classList.remove('error');
                char.style.cssText = 'font-family: Arial; font-size: 16px; color: black;';
            }
        });

        const quoteSubstring = currentQuote.substring(0, typedValue.length);

        if (typedValue === currentQuote) {
            const elapsedTime = (new Date().getTime() - startTime) / 1000;
            messageElement.innerHTML = `Congratulations! You typed the quote in ${elapsedTime.toFixed(2)} seconds.`;
            typedValueElement.disabled = true;
            clearTimeout(timer);
            window.location.href = 'success.html'; // 成功完成后跳转到 success.html
        }
    });
});
