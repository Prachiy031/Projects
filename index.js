//initially checked whether DOM is created then only js file is executed
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.content');
    const input = document.querySelector('#textbox');
    const todoContainer = document.querySelector('.todo-container');

    // Load existing todos from local storage
    const todos = JSON.parse(localStorage.getItem('todos')) || [];      //checked todos key is present in localstorage ..if present then convert into array..otherwise return empty array
    todos.forEach(todo => addToDoToDOM(todo)); // Display all todos

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const task = input.value.trim();
        if (task) {
            if(todos.length > 30){
                alert("Sorry you can only add maximum 30 tasks.");
                return;
            }
            if(todos.some(todo => todo.task === task)){
                alert("This task is already added in your to-do list. Please add new task");
                return;
            }
            const todo = { task, completed: false }; //otherwise add task in todos array
            todos.push(todo);
            addToDoToDOM(todo);
            saveTodos();
        }
        input.value = ''; // Clear the input field
    });

    todoContainer.addEventListener('click', (e) => {
        if (e.target.closest('.delete')) {
        const todoItem = e.target.closest('.todo-item');
        todoItem.classList.add('remove');
        setTimeout(() => {
            const task = todoItem.querySelector('label').textContent;
            removeToDoFromList(task);
            todoItem.remove();
            saveTodos();
        }, 300); // Match the duration of the CSS transition
    }else if (e.target.type === 'checkbox') {
            const todoItem = e.target.parentElement; // Selects parent element of checkbox
            const task = todoItem.querySelector('label').textContent; // Gets label text
            toggleComplete(task, e.target.checked); // Updates todos array
            alert(`Congrats! ${task} is Completed.`)
            saveTodos(); // Save todos to local storage
        }
    });

    function addToDoToDOM(todo) {
        const todoItem = document.createElement('div');
        todoItem.classList.add('todo-item');

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.checked = todo.completed;

        const label = document.createElement('label');
        label.textContent = todo.task;
        if (todo.completed) {
            label.style.textDecoration = 'line-through';
        }

        
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete');
        deleteButton.setAttribute('aria-label', 'Delete task'); // Add aria-label for accessibility
        deleteButton.title = 'Delete task'; // Add title attribute for tooltip
         
        const icon = document.createElement('i');
        icon.classList.add('fa', 'fa-trash'); // Use Font Awesome classes
        icon.style.color = '#120382'; // Set icon color
        deleteButton.appendChild(icon);

        todoItem.appendChild(checkbox);
        todoItem.appendChild(label);
        todoItem.appendChild(deleteButton);

        todoContainer.appendChild(todoItem);
    }

    function saveTodos() {
        localStorage.setItem('todos', JSON.stringify(todos));
    }

    function removeToDoFromList(task) {
        const index = todos.findIndex(todo => todo.task === task);
        if (index !== -1) {
            todos.splice(index, 1);
        }
    }

    function toggleComplete(task, completed) {
        const todo = todos.find(todo => todo.task === task);
        if (todo) {
            todo.completed = completed;
            const label = Array.from(document.querySelectorAll('label')).find(l => l.textContent === task);
            if (label) {
                label.style.textDecoration = completed ? 'line-through' : 'none';
            }
        }
    }
});
