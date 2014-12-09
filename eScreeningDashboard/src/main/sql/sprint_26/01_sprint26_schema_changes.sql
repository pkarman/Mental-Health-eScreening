alter table battery add column welcome_message TEXT NULL;

alter table battery add column complete_message TEXT NULL;

update battery set welcome_message = 
' <h1>Welcome to VA San Diego Healthcare System!</h1>
  <p>We thank you for your service to our country and look forward to assisting in your enrollment for VA healthcare and transition from active duty to Veteran status. </p>
  <p>The following eScreening Questionnaire will help your VA healthcare team to assess your needs and provide the best health and wellness options available to you. Please take your time and answer all questions as completely as possible. If you have any questions, please ask for assistance.</p>
' where battery_id = 5;

update battery set complete_message = '
<div class=\'moduleTemplateTitle\'>Thank You!</div><br/>
<div class=\'templateSectionTitle\'>
Please let the assistant know that you have completed your screen. They will provide you with a personalized summary of your screens. The results of this screen will be sent electronically to a Transition Case Manager for review.</div><br/>
<div class=\'templateSectionTitle\'>
The goal of OEF/OIF/OND Care Management is to help you maximize your VA services and benefits. Here are some services that we provide: </div><br/>
<div class=\'templateSectionTitle\'>- Care coordination and support with access to VA healthcare services & benefits</div>
<div class=\'templateSectionTitle\'>- Advocacy to address post-deployment health concerns</div>
<div class=\'templateSectionTitle\'>- Resources to address  employment, education or housing  concerns  </div>
<div class=\'templateSectionTitle\'>- Applying for VA, other government, and community benefits</div>
<div class=\'templateSectionTitle\'>- Resources for marriage, family, and spirituality concerns </div>
<div class=\'templateSectionTitle\'>- Aid with concerns about drinking or drug use</div>
<div class=\'templateSectionTitle\'>- Assistance if you are feeling sad, depressed or anxious</div>
<div class=\'templateSectionTitle\'>- Assistance with visual impairments</div>
<div class=\'templateSectionTitle\'>- Help if you really aren’t sure what you need, but things just don’t feel right</div><br/>
<div class=\'templateSectionTitle\'>You may ask to meet with a Transition Case Manager today to discuss any issues presented in this screen. You can also call the OEF/OIF/OND Care Management team at any point in the future for assistance. Their contact information is listed on your personalized summary.</div>
'  where battery_id = 5;
