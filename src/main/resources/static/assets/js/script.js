$(document).ready(function() {
    $('.quiz input[type="radio"]').click(function(){
        let subject  = $(this).closest(".quiz").data("subjectid");
        let question = $(this).attr("data-questionid");
        let answer  = $(this).val();

        $.ajax({
            type: "POST",
            url: '/submit-quiz-answer',
            data: {subjectId: subject, questionId: question, answerId: answer},
            success: function(response){

            },
            error: function(xhr,statur,error){

            }
        })
    });
   
});

// document.addEventListener("DOMContentLoaded", function(){
//     let radioButtons = document.querySelectorAll('.quiz input[type="radio"]');
//     radioButtons.forEach(function(radio){
//         radio.addEventListener('change', function(event){
//             let answer   = +event.target.value;
//             let question = +this.getAttribute("data-questionid");
//             let subject  = +document.querySelector(".quiz").getAttribute("data-subjectid");

//             console.log("answer",answer);
//             console.log("question",question);
//             console.log("subject",subject);

//             let formData = new FormData();
//             formData.append("subjectid",subject);
//             formData.append("questionid",question);
//             formData.append("answerid",answer);

//             fetch('/submit-quiz-answer',{
//                 method:"POST",
//                 body:formData
//             })
//             .then(response=>{

//             })
//             .catch(error=>{
//                 console.log("Error:", error);
//             })
//         });
//     })
// })