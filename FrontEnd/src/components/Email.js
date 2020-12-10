import React, {Component} from 'react';
import{ init } from 'emailjs-com';
init("user_Tz1faAvOvevYfccbPcX8C");

class Email extends Component {
    constructor(props) {
        super(props);
        this.state = { feedback: '', name: 'Name', email: 'fernando.ochoa9991@gmail.com' };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


render() {
    return (
        <form className="test-mailing">
    	<h1>Type your email to specified advisor here.</h1>
    	<div>
      	<textarea
        	id="test-mailing"
        	name="test-mailing"
        	onChange={this.handleChange}
        	placeholder="Enter your text here."
        	required
        	value={this.state.feedback}
        	style={{width: '100%', height: '150px'}}
      	/>
    	</div>
    	<input type="button" value="Submit" className="btn btn--submit" onClick={this.handleSubmit} />
  	</form>
	)
}

handleChange(event) {
    this.setState({feedback: event.target.value})
  }

  handleSubmit(event) {
    const templateId = 'template_id';

	this.sendFeedback(templateId, {message_html: this.state.feedback, from_name: this.state.name, reply_to: this.state.email})
  }

  sendFeedback (templateId, variables) {
	window.emailjs.send(
  	'gmail', templateId,
  	variables
  	).then(res => {
    	console.log('Email successfully sent!')
  	})
  	// Handle errors here however you like, or use a React error boundary
  	.catch(err => console.error('Oh well, you failed. Here some thoughts on the error that occured:', err))
  }
}

export default Email;