import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Tabs, Tab, Alert } from 'react-bootstrap';

import { userActions, empActions } from '../_actions';

class EMPHomePage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            name: '',
            salary: '',
            startDate: '',
            mortId: '',
            submittedForm: false,
            dispatchedForm: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitForm = this.handleSubmitForm.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmitForm(e) {
        e.preventDefault();

        const { userId } = this.props;

        this.setState({ submittedForm: true });

        const { name, salary, startDate, mortId } = this.state;
        const { dispatch } = this.props;

        if (name && salary && startDate && mortId) {
            this.setState({ dispatchedForm: true });

            // this should wait until a result is returned from patch before applying
            Promise.all([
                dispatch(empActions.submit(userId, name, salary, startDate))
            ]).then(() => {
                dispatch(empActions.apply(userId, mortId));
            });
        }
    }

    render() {
        const ackVisibilityState = this.state.dispatchedForm ? "visible" : "hidden";

        const { user } = this.props;
        const { name, salary, startDate, mortId, submittedForm } = this.state;

        return (
            <div>
                <h1>EMP</h1>
                <p>
                    <Link to="/emp/login">Logout</Link>
                </p>
                <Tabs defaultActiveKey={1} id="emp-tabs">
                    <Tab eventKey={1} title="Welcome">
                        <p></p>
                        <h3>Welcome to the EMP Control Panel</h3>
                        <p>Navigate and submit forms.</p>
                    </Tab>
                    <Tab eventKey={2} title="Forward Request">
                        <p></p>
                        <form name="form" onSubmit={this.handleSubmitForm}>
                            <div className={'form-group' + (submittedForm && !name ? ' has-error' : '')}>
                                <label htmlFor="name">Name</label>
                                <input type="text" className="form-control" name="name" value={name} onChange={this.handleChange} />
                                {submittedForm && !name &&
                                    <div className="help-block">Name is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !salary ? ' has-error' : '')}>
                                <label htmlFor="salary">Salary</label>
                                <input type="number" className="form-control" name="salary" value={salary} onChange={this.handleChange} />
                                {submittedForm && !salary &&
                                    <div className="help-block">Salary is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !startDate ? ' has-error' : '')}>
                                <label htmlFor="startDate">Employment Start Date</label>
                                <input type="text" className="form-control" name="startDate" value={startDate} onChange={this.handleChange} />
                                {submittedForm && !startDate &&
                                    <div className="help-block">Start date is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mortId ? ' has-error' : '')}>
                                <label htmlFor="mortId">Mortgage ID</label>
                                <input type="text" className="form-control" name="mortId" value={mortId} onChange={this.handleChange} />
                                {submittedForm && !mortId &&
                                    <div className="help-block">Mortgage ID is required</div>
                                }
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </form>

                        <Alert style={{ visibility: ackVisibilityState }} bsStyle="success">
                            <strong>Request Submitted</strong>
                        </Alert>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { empAuthentication } = state;
    const { user } = empAuthentication;
    const userId = state.empAuthentication.user.id;

    return {
        user,
        userId
    };
}

const connectedEMPHomePage = connect(mapStateToProps)(EMPHomePage);
export { connectedEMPHomePage as EMPHomePage };