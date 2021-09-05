import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react"
import CreateMenu from './components/CreateMenu';

function App() {

  const [question, setQuestion] = useState(null)
  const [buttonPopup, setButtonPopup] = useState(false)

  useEffect(() => {

    if (!question){
      fetch("http://localhost:8889/api/quizzes/random")
      .then((response) => response.json())
      .then((data) => {
        setQuestion(data);
      })
    }
  }, [question])

  function pressButton(event){
    /*
    const num = event.target.value
    console.log(event.target.value)
    if (answer.length > 0 && !answer.includes(num)){
      setAnswer({answer: [...answer, num]})
    }
    else {
      let array = [...answer]
      let index = array.indexOf(num)
      array.splice(index, 1)
      setAnswer(array)
    }
    */
  }

  function submitAnswer(){
    if (document.getElementById("submitButton").value === "Next"){
      window.location.reload()
    }

    else {
      let array = [];
      if (document.getElementById("firstChoice").checked){
        array.push(0)
        document.getElementById("button1").style.borderColor ="blue"
      }
      if (document.getElementById("secondChoice").checked){
        array.push(1)
        document.getElementById("button2").style.borderColor ="blue"
      }
      if (document.getElementById("thirdChoice").checked){
        array.push(2)
        document.getElementById("button3").style.borderColor ="blue"
      }
      if (document.getElementById("fourthChoice").checked){
        array.push(3)
        document.getElementById("button4").style.borderColor ="blue"
      }
      const answer = {answer: array}
      //console.log("json", JSON.stringify(answer))
      fetch(`http://localhost:8889/api/quizzes/${question.id}/solve`, {
        method: "POST",
        headers: {
          'Accept': 'application/json, text/plain, */*',
          "content-type": "application/json"
        },
        body: JSON.stringify(answer)
      }).then((response) => response.json()
      ).then((data) => {
        if (data.correct[0]){
          document.getElementById("button1").style.backgroundColor = "green"
        } else {
          document.getElementById("button1").style.backgroundColor = "red"
        }
        document.getElementById("firstChoice").checked = false;

        if (data.correct[1]){
          document.getElementById("button2").style.backgroundColor = "green"
        } else {
          document.getElementById("button2").style.backgroundColor = "red"
        }
        document.getElementById("secondChoice").checked = false;

        if (data.correct[2]){
          document.getElementById("button3").style.backgroundColor = "green"
        } else {
          document.getElementById("button3").style.backgroundColor = "red"
        }
        document.getElementById("thirdChoice").checked = false;

        if (data.correct[3]){
          document.getElementById("button4").style.backgroundColor = "green"
        } else {
          document.getElementById("button4").style.backgroundColor = "red"
        }
        document.getElementById("fourthChoice").checked = false;
      })

      document.getElementById("submitButton").value = "Next"
    }
  }

  function deleteQuestion(){
    fetch(`http://localhost:8889/api/quizzes/${question.id}`, {
      method: 'DELETE'
    }).catch(err => console.log(err))
    setQuestion(null)
  }

  function createQuestion(){
    setButtonPopup(true)
  }


  return (
    <div className="App">
      {question ? (
      <div className="wrapper">
        <div id="question">
        <h1>{question.text}</h1>
        </div>
        
        
        <div id="all-buttons">
        <div id="options-container">
          <div className="ck-button" id="button1">
            <label>
              <input type="checkbox" id="firstChoice" onChange={pressButton} value="0"/><span>{question.options[0]}</span>
            </label>
          </div>
          <div className="ck-button" id="button2">
            <label>
              <input type="checkbox" id="secondChoice" onChange={pressButton} value="1"/><span>{question.options[1]}</span>
            </label>
          </div>
        </div>

        <div id="options-container">
          <div className="ck-button" id="button3">
            <label>
              <input type="checkbox" id="thirdChoice" onChange={pressButton} value="2"/><span>{question.options[2]}</span>
            </label>
          </div>
          <div className="ck-button" id="button4">
            <label>
              <input type="checkbox" id="fourthChoice" onChange={pressButton} value="3"/><span>{question.options[3]}</span>
            </label>
          </div>
        </div>
        </div>

        <input type="button" id="submitButton" value="Submit" onClick={submitAnswer}/>

        <input type="button" id="deleteButton" value="Delete" onClick={deleteQuestion}/>

        <input type="button" id="createButton" value="Create" onClick={createQuestion}/>
      
        <CreateMenu trigger={buttonPopup} setTrigger={setButtonPopup}>
        </CreateMenu>
      </div>
      ):"Loading Data"}
    </div>
  );
}

export default App;
