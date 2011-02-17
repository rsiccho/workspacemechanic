var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2285, "sl" : 14, "el" : 99, "name" : "UsageLongCompatibleReturnValueTest",
    "methods" : [
              {"sl" : 19, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 38, "sc" : 5},  {"sl" : 40, "el" : 53, "sc" : 5},  {"sl" : 55, "el" : 68, "sc" : 5},  {"sl" : 70, "el" : 83, "sc" : 5},  {"sl" : 85, "el" : 98, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_242" : {
					  "name" : "returnInt",
					  "pass" : true ,
					  "methods" : [{"sl": 70 },],
					  "statements" : [{"sl": 72 },{"sl": 73 },{"sl": 74 },{"sl": 76 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 82 },]
					  },
		"test_54" : {
					  "name" : "returnByte",
					  "pass" : true ,
					  "methods" : [{"sl": 25 },],
					  "statements" : [{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 31 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 37 },]
					  },
		"test_50" : {
					  "name" : "returnShort",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },],
					  "statements" : [{"sl": 42 },{"sl": 43 },{"sl": 44 },{"sl": 46 },{"sl": 48 },{"sl": 49 },{"sl": 50 },{"sl": 52 },]
					  },
		"test_235" : {
					  "name" : "returnLong",
					  "pass" : true ,
					  "methods" : [{"sl": 85 },],
					  "statements" : [{"sl": 87 },{"sl": 88 },{"sl": 89 },{"sl": 91 },{"sl": 93 },{"sl": 94 },{"sl": 95 },{"sl": 97 },]
					  },
		"test_238" : {
					  "name" : "returnChar",
					  "pass" : true ,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 67 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 54   ] ,
  [  ] ,
  [ 54   ] ,
  [ 54   ] ,
  [ 54   ] ,
  [  ] ,
  [ 54   ] ,
  [  ] ,
  [ 54   ] ,
  [ 54   ] ,
  [ 54   ] ,
  [  ] ,
  [ 54   ] ,
  [  ] ,
  [  ] ,
  [ 50   ] ,
  [  ] ,
  [ 50   ] ,
  [ 50   ] ,
  [ 50   ] ,
  [  ] ,
  [ 50   ] ,
  [  ] ,
  [ 50   ] ,
  [ 50   ] ,
  [ 50   ] ,
  [  ] ,
  [ 50   ] ,
  [  ] ,
  [  ] ,
  [ 238   ] ,
  [  ] ,
  [ 238   ] ,
  [ 238   ] ,
  [ 238   ] ,
  [  ] ,
  [ 238   ] ,
  [  ] ,
  [ 238   ] ,
  [ 238   ] ,
  [ 238   ] ,
  [  ] ,
  [ 238   ] ,
  [  ] ,
  [  ] ,
  [ 242   ] ,
  [  ] ,
  [ 242   ] ,
  [ 242   ] ,
  [ 242   ] ,
  [  ] ,
  [ 242   ] ,
  [  ] ,
  [ 242   ] ,
  [ 242   ] ,
  [ 242   ] ,
  [  ] ,
  [ 242   ] ,
  [  ] ,
  [  ] ,
  [ 235   ] ,
  [  ] ,
  [ 235   ] ,
  [ 235   ] ,
  [ 235   ] ,
  [  ] ,
  [ 235   ] ,
  [  ] ,
  [ 235   ] ,
  [ 235   ] ,
  [ 235   ] ,
  [  ] ,
  [ 235   ] ,
  [  ] ,
  [  ] 
];