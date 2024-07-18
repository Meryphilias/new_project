document.addEventListener('DOMContentLoaded', function(){
       
    function printName(){
            
        const name = document.getElementById("search").value;
        document.getElementById("result").innerText = name; 
     
    }
    
    const imageUrl = [
        ['img/tem/0/0.jpg', 'img/tem/0/1.jpg', 'img/tem/0/2.jpg'],
        ['img/tem/1/0.jpg', 'img/tem/1/1.jpg', 'img/tem/1/2.jpg']
    ];

    function create_template(count){
        const template = document.getElementById('template_area');
        for(let i = 0; i < count; i++){
            const box = document.createElement('div');
            box.id = 'content'+ (i + 1);
            box.textContent = box.id;
            box.addEventListener('click', function(){   
                window.location.href = `/ppttype?b=${box.id}`;

            });
            for(let j = 0; j < imageUrl[i].length; j++){
                box.className = 'template_box';
                const image = document.createElement('img');
                image.src = imageUrl[i][j];
                if(j == 0){
                    image.className = 'image0';
                }
                else if(j == 2){
                    image.className = 'image2';
                }
                image.style.zIndex = 10 - j;
                box.appendChild(image);                    
            }
            template.appendChild(box);
        }
    }

    const num = 4;
    create_template(num);
        
});