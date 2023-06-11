
# UML

Lo schema UML è stato generato usando il tool [yuml](https://yuml.me/diagram/scruffy/class/draw) dal seguente codice:

```

[Agent|-agentName: String;-problem: Problem;-classifier: Classifier|+learn();+predict(Instance)]

[Problem|-problemName: String;-problemFileName: String;-problemFile: File;-data: Instances|+loadProblem();+initializeProblem();+saveProblem();+addInstance(i: Instance)]

[Classifier|+buildClassifier(Instances);+classifyInstance(Instance)]
[Instances|-Instances: List<Instance>;-Attributes: List<Attribute>|+classAttribute(): Attribute]
[Instance|+dataset(): Instances;+classAttribute(): Attribute]
[Attribute;-Name;-Type]
[Evaluation|+Evaluation(train: Instances);+evaluateModel(c: Classifier, test: Instances)|+confusionMatrix();+errorRate();+recall();+precision();+kappa()]

[Agent]->[Problem]
[Agent]->[Classifier]
[Problem]->[Instances]

[Instances]++-[Instance]
[Instances]++-[Attribute]
[Instance]++-[Attribute]
[Classifier]->[Instances]

[Evaluation]++-[Classifier]
[Evaluation]->[Instances]

```