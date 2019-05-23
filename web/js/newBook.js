/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate(){
    let valid = true;
    let nameBook = document.getElementById('nameBook');
    let author = document.getElementById('author');
    let yearPublished = document.getElementById('yearPublished');
    let isbn = document.getElementById('isbn');
    let count = document.getElementById('count');
    
    if(nameBook.value < 1){
        nameBook.className = 'invalid';
        valid = false;
    }else{
        nameBook.className = '';
        nameBook = true;
    }
    
    if(author.value < 1){
        author.className = 'invalid';
        valid = false;
    }else{
        author.className = '';
        valid = true;
    }
    
    if(yearPublished.value < 1){
        yearPublished.className = 'invalid';
        valid = false;
    }else{
        yearPublished.className = '';
        valid = true;
    }
    if(isbn.value < 1){
        isbn.className = 'invalid';
        valid = false;
    }else{
        isbn.className = '';
        valid = true;
    }
    if(count.value < 1){
        count.className = 'invalid';
        valid = false;
    }else{
        count.className = '';
        valid = true;
    }
    return valid;
}
