import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react"

function App() {

  const [question, setQuestion] = useState(null)

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
    let array = [];
    if (document.getElementById("firstChoice").checked){
      array.push(0)
    }
    if (document.getElementById("secondChoice").checked){
      array.push(1)
    }
    if (document.getElementById("thirdChoice").checked){
      array.push(2)
    }
    if (document.getElementById("fourthChoice").checked){
      array.push(3)
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
    ).then((data) => console.log(data))
  }


  return (
    <div className="App">
      {question ? (
      <div>
        <div id="question">
        <h1>{question.text}</h1>
        </div>
        
        
        <div id="all-buttons">
        <div id="options-container">
          <div id="ck-button">
            <label>
              <input type="checkbox" id="firstChoice" onChange={pressButton} value="0"/><span>{question.options[0]}</span>
            </label>
          </div>
          <div id="ck-button">
            <label>
              <input type="checkbox" id="secondChoice" onChange={pressButton} value="1"/><span>{question.options[1]}</span>
            </label>
          </div>
        </div>

        <div id="options-container">
          <div id="ck-button">
            <label>
              <input type="checkbox" id="thirdChoice" onChange={pressButton} value="2"/><span>{question.options[2]}</span>
            </label>
          </div>
          <div id="ck-button">
            <label>
              <input type="checkbox" id="fourthChoice" onChange={pressButton} value="3"/><span>{question.options[3]}</span>
            </label>
          </div>
        </div>
        </div>

        <input type="button" value="Submit" onClick={submitAnswer}/>
      
      </div>
      ):"Loading Data"}
    </div>
  );
}

export default App;
