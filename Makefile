# can be configured by using `SONAR_HOST=n` with `make`
SONAR_HOST = http://101.132.102.201:9000

# run all analysis tasks
all: entity-affiliation entity-domain entity-paper entity-publication entity-researcher task-citation-analysis task-domain-prediction task-impact-analysis task-partnership-analysis task-reviewer-recommendation
.PHONY: all

# $(1) is the name of service
# $(2) is sonar cube project token
define analysis
	cd $(1)/ && mvn clean verify sonar:sonar \
    	-Dsonar.projectKey=org.NJU-SE-17-advanced-se:$(1) \
    	-Dsonar.host.url=$(SONAR_HOST) \
    	-Dsonar.login=$(2)
endef

entity-affiliation:
	$(call analysis,entity-affiliation,fc6226328cbb9efeaddf7b3c002cd46c946e5361)
.PHONY: entity-affiliation

entity-domain:
	$(call analysis,entity-domain,fec90eef58759c523e107ea87c9731c84e035465)
.PHONY: entity-domain

entity-paper:
	$(call analysis,entity-paper,e328bf8559595ec65b4619aaff963fb23eb043aa)
.PHONY: entity-paper

entity-publication:
	$(call analysis,entity-publication,50ae0cd7fe262db7127c47a824e2d5fbda68be9a)
.PHONY: entity-publication

entity-researcher:
	$(call analysis,entity-researcher,f2fe9c0e95704d3735f3344e0eb540f102d4e3c0)
.PHONY: entity-researcher

task-citation-analysis:
	$(call analysis,task-citation-analysis,68dfcd2399a49d8d2322a818dc12e5fb81b9d508)
.PHONY: task-citation-analysis

task-domain-prediction:
	$(call analysis,task-domain-prediction,796c57037e34dd7e9ac9b7a7254e90159912f56f)
.PHONY: task-domain-prediction

task-impact-analysis:
	$(call analysis,task-impact-analysis,40a0a728d53daf057af51f5c95db539e35634f9e)
.PHONY: task-impact-analysis

task-partnership-analysis:
	$(call analysis,task-partnership-analysis,9ebf0fcbcd1ab66ac3f62f92a236d09d74b51225)
.PHONY: task-partnership-analysis

task-reviewer-recommendation:
	$(call analysis,task-reviewer-recommendation,862f8e6fe6d7d91b6d6ad5792b647997d76e44e6)
.PHONY: task-reviewer-recommendation
