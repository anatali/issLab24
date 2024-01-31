### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('servicemathmemoArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice', graph_attr=nodeattr):
          storage=Custom('storage','./qakicons/symActorWithobjSmall.png')
          servicemath=Custom('servicemath','./qakicons/symActorSmall.png')
          actionexec=Custom('actionexec','./qakicons/symActorSmall.png')
          dummy=Custom('dummy','./qakicons/symActorSmall.png')
     f=Custom('f','./qakicons/server.png')
     servicemath >> Edge(color='magenta', style='dotted', decorate='true', label='<currentMsg &nbsp; >',  fontcolor='green') >> actionexec
     actionexec >> Edge(color='magenta', style='solid', decorate='true', label='<getfibo<font color="darkgreen"> getfiboanswer</font> &nbsp; >',  fontcolor='magenta') >> storage
     actionexec >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemath
     f >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> servicemath
diag
