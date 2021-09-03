import React, { useState } from "react"
import './CreateMenu.css'

function CreateMenu(props){
    const [qtitle, setTitle] = useState("")
    const [body, setBody] = useState("")
    const [option1, setOption1] = useState("")
    const [option2, setOption2] = useState("")
    const [option3, setOption3] = useState("")
    const [option4, setOption4] = useState("")


    function createQuestion(){
        let array = [];
        if (document.getElementById("firstCorrect").checked){
            array.push(0)
        }
        if (document.getElementById("secondCorrect").checked){
            array.push(1)
        }
        if (document.getElementById("thirdCorrect").checked){
            array.push(2)
        }
        if (document.getElementById("fourthCorrect").checked){
            array.push(3)
        }

        const question = {
            title: qtitle,
            text: body,
            options: [option1, option2, option3, option4],
            answer: array
        }

        //console.log("jsonCreateQu", JSON.stringify(finalQuestion))

        
        fetch(`http://localhost:8889/api/quizzes/`, {
            method: "POST",
            headers: {
                'Accept': 'application/json, text/plain, */*',
                "content-type": "application/json"
         },
        body: JSON.stringify(question)
        }).then((response) => response.json()
        ).then((data) => {
            console.log(data)
        }).catch(e => {
            console.log("error", e)
        })

        setTitle("")
        setBody("")
        setOption1("")
        setOption2("")
        setOption3("")
        setOption4("")

        props.setTrigger(false)

    }

    return (props.trigger) ? (
        <div className="popup">
            <div className="popup-inner">
                <h1>Create a Question!</h1>
            <form>
                <label>Question title:</label>
                <textarea 
                 required
                 value={qtitle}
                 onChange={(e) => setTitle(e.target.value)}
                >

                </textarea>
                <label>Question content:</label>
                <textarea
                 required
                 value={body}
                 onChange={(e) => setBody(e.target.value)}
                >

                </textarea>

                <div className="options">
                    <label>First option correct?:</label>
                    <input type="checkbox" id="firstCorrect" value="0"/>
                </div>
                <textarea 
                 required
                 value={option1}
                 onChange={(e) => setOption1(e.target.value)}
                >

                </textarea>

                <div className="options">
                    <label>Second option correct?:</label>
                    <input type="checkbox" id="secondCorrect" value="1"/>
                </div>
                <textarea 
                 required
                 value={option2}
                 onChange={(e) => setOption2(e.target.value)}
                >

                </textarea>

                <div className="options">
                    <label>Third option correct?:</label>
                    <input type="checkbox" id="thirdCorrect" value="2"/>
                </div>
                <textarea 
                 required
                 value={option3}
                 onChange={(e) => setOption3(e.target.value)}
                >

                </textarea>

                <div className="options">
                    <label>Fourth option correct?:</label>
                    <input type="checkbox" id="fourthCorrect" value="3"/>
                </div>
                <textarea 
                 required
                 value={option4}
                 onChange={(e) => setOption4(e.target.value)}
                >

                </textarea>






                <input type="button" id="createButton" value="Create" onClick={createQuestion}/>
            </form>
                <input type="button" id="cancelButton" value="Cancel" onClick={() => props.setTrigger(false)}/>
                {props.children}
            </div>
        </div>
    ) : "";
}

export default CreateMenu