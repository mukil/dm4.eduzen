package de.deepamehta.plugins.eduzen.service;

import de.deepamehta.core.service.ClientState;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.plugins.eduzen.model.Resource;
import de.deepamehta.plugins.eduzen.model.ResourceTopic;

public interface EduzenService extends PluginService {

    Resource createContent(ResourceTopic topic, ClientState clientState);
    
    /**
    
    Identity authenticate(uid, credentials);
    
    // Queries for tutors and practiceSheet creators.. 
    
    PracticeSheet createPracticeSheet(course); // use POSTs
    
    PracticeSheet updatePracticeSheet(practiceSheet);
    
    PracticeSheet deletePracticeSheet(practiceSheet);
    
    void associateAssignmentToPracticeSheet(task, practiceSheet); // use PUTs for do_ and edit_
    
    PracticeSheet updateAssociatedAssignment(practiceSheet, assignment);
    
    // Queries to browse assignments/exercises..
    
    void getAssignmentByTopicalArea();
    
    // Queries for students
    
    Course signupCourse(identity, lv);
    
    Set<Course> getActiveCourses(identity); // GETs can never be void
    
    List<Course> getAllCourses(identity);
    
    List<Module> getModules(identity);
    
    List<Module> getCoursesByModule(module);
    
    List<PracticeSheet> getPracticeSheetsByCourse();
    
    // Quries for correctors ...
    
    List<Task> getUnprocessedSuggestions(task);
    
    // Queries around fetching associated content ...
    
    List<Content> getDirectAssociatedContents(topicalArea);
    
    List<Content> getDirectAssociatedContents(task);
    
    List<Content> getDirectAssociatedContents(suggestion);
    
    List<Content> getDirectAssociatedContents(remark);
    
    // Queries around topical areas ...
    
    List<TopicalArea> getTopicalAreasByCourse(course);
    
    List<TopicalArea> getTopicalAreasByModule(module);
    
    List<TopicalArea> getTopicalAreasByTask(task);
    
    List<TopicalArea> getTopicalAreasBySuggestion(suggestion);
    
    List<TopicalArea> getTopicalAreasByRemark(remark);
    
    List<TopicalArea> getTopicalAreasByPracticeSheet(practiceSheet);
    
    List<TopicalArea> getUnknownItemsByPracticeSheet(practiceSheet);
    
    List<TopicalArea> getBasements(topicalArea);
    
    List<TopicalArea> getAdvancements(topicalArea);
    
     **/

}
