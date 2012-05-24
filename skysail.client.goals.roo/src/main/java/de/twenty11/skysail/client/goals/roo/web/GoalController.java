package de.twenty11.skysail.client.goals.roo.web;

import de.twenty11.skysail.client.goals.roo.Goal;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/goals")
@Controller
@RooWebScaffold(path = "goals", formBackingObject = Goal.class)
public class GoalController {
}
