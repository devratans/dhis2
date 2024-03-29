var validationRules = {
    "user" : {
        "username" : {
            "required" : true,
            "rangelength" : [ 2, 140 ]
        },
        "firstName" : {
            "required" : true,
            "rangelength" : [ 2, 140 ]
        },
        "surname" : {
            "required" : true,
            "rangelength" : [ 2, 140 ]
        },
        "password" : {
            "required" : true,
            "password" : true,
            "notequalto" : "#username",
            "rangelength" : [ 8, 35 ]
        },
        "rawPassword" : {
            "required" : true,
            "password" : true,
            "rangelength" : [ 8, 35 ]
        },
        "retypePassword" : {
            "required" : true,
            "equalTo" : "#rawPassword"
        },
        "email" : {
            "email" : true,
            "rangelength" : [ 0, 160 ]
        },
        "phoneNumber" : {
            "rangelength" : [ 0, 80 ]
        },
        "roleValidator" : {
            "required" : true
        }
    },
	"profile" : {
		"email" : {
            "email" : true,
            "rangelength" : [ 0, 160 ]
        },
		"phoneNumber" : {
            "rangelength" : [ 0, 80 ]
        }
	},
    "role" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 140 ]
        },
        "description" : {
            "required" : true,
            "rangelength" : [ 2, 210 ]
        }
    },
    "userGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 210 ],
            "alphanumericwithbasicpuncspaces" : true
        },
        "memberValidator" : {
            "required" : true
        }
    },
    "organisationUnit" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "shortName" : {
            "required" : true,
            "rangelength" : [ 2, 49 ]
        },
        "code" : {
            "rangelength" : [ 0, 25 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : false
        },
        "openingDate" : {
            "required" : true
        },
        "longitude" : {
        	"number" : true,
        	"min": -180,
        	"max": 180
        },
        "latitude" : {
        	"number" : true,
        	"min": -90,
        	"max": 90
        },
        "url" : {
            "url" : true,
            "rangelength" : [ 0, 255 ]
        },
        "contactPerson" : {
            "rangelength" : [ 0, 255 ]
        },
        "address" : {
            "rangelength" : [ 0, 255 ]
        },
        "email" : {
            "email" : true,
            "rangelength" : [ 0, 250 ]
        },
        "phoneNumber" : {
            "rangelength" : [ 0, 255 ]
        }
    },
    "organisationUnitGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        }
    },
    "organisationUnitGroupSet" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "required" : true,
            "rangelength" : [ 2, 255 ]
        }
    },
    "dataEntry" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 4, 100 ]
        }
    },
    "section" : {
        "sectionName" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "selectedList" : {
            "required" : true
        }
    },
    "dataSet" : {
        "name" : {
            "required" : true,
            "alphanumericwithbasicpuncspaces" : true,
            "rangelength" : [ 4, 150 ]
        },
        "shortName" : {
            "required" : true,
            "alphanumericwithbasicpuncspaces" : true,
            "rangelength" : [ 2, 25 ]
        },
        "code" : {
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : false,
            "rangelength" : [ 0, 25 ]
        },
        "expiryDays": {
            "digits" : true
        },
        "frequencySelect" : {
            "required" : true
        }
    },
    "sqlView" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 50 ]
        },
        "description" : {
            "required" : true
        },
        "sqlquery" : {
            "required" : true
        }
    },
    "dataLocking" : {
        "selectedPeriods" : {
            "required" : true
        },
        "selectedDataSets" : {
            "required" : true
        }
    },
    "dataBrowser" : {
        "periodTypeId" : {
            "required" : true
        },
        "mode" : {
            "required" : true
        }
    },
    "minMax" : {
        "dataSetIds" : {
            "required" : true
        }
    },
    "concept" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 10 ]
        }
    },
    "dataElement" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 150 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : true
        },
        "shortName" : {
            "required" : true,
            "rangelength" : [ 2, 25 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : true
        },
        "alternativeName" : {
            "rangelength" : [ 3, 150 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : true
        },
        "code" : {
            "rangelength" : [ 0, 25 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : false
        },
        "formName" : {
            "rangelength" : [ 2, 150 ],
            "alphanumericwithbasicpuncspaces" : false,
            "notOnlyDigits" : false
        },
        "url" : {
            "url" : true,
            "rangelength" : [ 0, 255 ]
        }
    },
    "dateElementCategoryCombo" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "selectedList" : {
            "required" : true
        }
    },
    "dateElementCategory" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "conceptId" : {
            "required" : true
        },
        "memberValidator" : {
            "required" : true
        }
    },
    "dataElementGroup" : {
        "name" : {
            "required" : true,
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : true,
            "rangelength" : [ 2, 160 ]
        }
    },
    "dataElementGroupSet" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "required" : true,
            "rangelength" : [ 2, 255 ]
        }
    },
    "dataDictionary" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "rangelength" : [ 0, 255 ]
        },
        "region" : {
            "rangelength" : [ 0, 255 ]
        },
        "memberValidator" : {
            "required" : true
        },
        "memberValidatorIn" : {
            "required" : true
        }
    },
    "indicator" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 150 ],
            "alphanumericwithbasicpuncspaces" : true,
            "nostartwhitespace" : true
        },
        "shortName" : {
            "required" : true,
            "rangelength" : [ 2, 25 ],
            "alphanumericwithbasicpuncspaces" : true
        },
        "alternativeName" : {
            "rangelength" : [ 3, 150 ],
            "alphanumericwithbasicpuncspaces" : true
        },
        "code" : {
            "rangelength" : [ 0, 25 ],
            "alphanumericwithbasicpuncspaces" : true,
            "notOnlyDigits" : false
        },
        "url" : {
            "url" : true,
            "rangelength" : [ 0, 255 ]
        },
        "indicatorTypeId" : {
            "required" : true
        },
        "denominator" : {
            "required" : true
        }
    },
    "indicatorGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
    "indicatorGroupSet" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "required" : true,
            "rangelength" : [ 2, 255 ]
        }
    },
    "indicatorType" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 150 ],
            "alphanumericwithbasicpuncspaces" : true
        },
        "factor" : {
            "required" : true,
            "rangelength" : [ 1, 10 ],
            "digits" : true
        }
    },
    "validationRule" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "rangelength" : [ 2, 160 ]
        },
        "periodTypeName" : {
            "required" : true
        },
        "operator" : {
            "required" : true
        },
        "leftSideExpression" : {
            "required" : true
        },
        "rightSideExpression" : {
            "required" : true
        }
    },
    "validationRuleGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 160 ]
        },
        "description" : {
            "rangelength" : [ 2, 160 ]
        }
    },
    "constant" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 230 ]
        },
        "value" : {
            "required" : true
        }
    },
    "chartGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 230 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
    "reportGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 230 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
    "reportTableGroup" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 3, 230 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
    "attribute" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 230 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
    "attributeOption" : {
        "name" : {
            "required" : true,
            "rangelength" : [ 2, 230 ],
            "alphanumericwithbasicpuncspaces" : true
        }
    },
	"dataMart" : {
		"name" : {
			"required": true
		}
	},
	"patientAttributeGroup" : {
		"name" : {
			"required" : true,
			"rangelength" : [ 2,160 ]
		},
		"description" : {
			"required" : true,
			"rangelength" : [ 2, 255 ]
		}
	},
	"emailSettings" : {
		"smtpHostName" : {
			"required" : true
		},
		"smtpUsername" : {
			"required" : true
		},
		"smtpPassword" : {
			"required" : true
		}
	},
	"SMSConfig" : {
		"pollingInterval" : {
			"required" : true,
			"digits" : true
		},
		"serverPhoneNumber" : {
			"digits" : true
		}
	},
	"autoUpdateClient" : {
		"version" : {
			"required" : true,
			"number" : true
		}
	}
};
